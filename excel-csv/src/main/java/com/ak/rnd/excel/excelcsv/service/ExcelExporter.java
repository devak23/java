package com.ak.rnd.excel.excelcsv.service;

import com.ak.rnd.excel.excelcsv.config.LogTime;
import com.ak.rnd.excel.excelcsv.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class ExcelExporter {
    private static final int MAX_EXCEL_ROW_LIMIT = 1000000;

    @LogTime
    public Optional<ByteArrayInputStream> downloadDataToExcel(String key, Flux<?> fluxOfItems, Map<String, String> headerMethodMap) {
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            final Workbook workbook = new SXSSFWorkbook();
            Sheet sheet = workbook.createSheet(key);
            createHeaders(sheet, headerMethodMap);

            AtomicInteger rowIndex = new AtomicInteger(0);
            AtomicInteger sheetCounter = new AtomicInteger(0);
            AtomicReference<Sheet> currentSheet = new AtomicReference<>(sheet);

            fluxOfItems
                    .parallel()
                    .subscribe( item -> {
                        if (rowIndex.get() >= MAX_EXCEL_ROW_LIMIT) {
                            Sheet newSheet = workbook.createSheet(key + "-" + sheetCounter.incrementAndGet());
                            createHeaders(newSheet, headerMethodMap);
                            currentSheet.set(newSheet);
                            rowIndex.set(0);
                        }
                        Field[] fields = item.getClass().getDeclaredFields();
                        Map<String, Field> nameFieldMap = createNameFieldMap(fields);

                        Row row = currentSheet.get().createRow(rowIndex.incrementAndGet());
                        int count = 0;
                        for (String methodName: headerMethodMap.keySet()) {
                            Field f = nameFieldMap.get(methodName);
                            f.setAccessible(true);
                            try {
                                row.createCell(count++).setCellValue((String) f.get(item));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });

            workbook.write(baos);
            return Optional.of(new ByteArrayInputStream(baos.toByteArray()));
        } catch (IOException e) {
            log.error("Error exporting Excel", e);
            return Optional.empty();
        }
    }

    private Map<String, Field> createNameFieldMap(Field[] fields) {
        Map<String, Field> nameFieldMap = new HashMap<>(fields.length * 2);
        for (Field f : fields) {
            nameFieldMap.put(f.getName(), f);
        }
        return nameFieldMap;
    }

    private void createHeaders(final Sheet sheet, Map<String, String> headerMethodMap) {
        Row headerRow = sheet.createRow(0);
        int count = 0;
        for(String key: headerMethodMap.keySet()) {
            headerRow.createCell(count++).setCellValue(headerMethodMap.get(key));
        }
    }
}
