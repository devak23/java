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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class ExcelExporter {
    private static final String SHEET_TITLE = "EmployeeData";
    private static final int MAX_EXCEL_ROW_LIMIT = 1000000;

    @LogTime
    public Optional<ByteArrayInputStream> downloadEmployeesToFile(Flux<Employee> employeeFlux) {
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            final Workbook workbook = new SXSSFWorkbook();
            Sheet sheet = workbook.createSheet(SHEET_TITLE);
            createHeaders(sheet);

            AtomicInteger rowIndex = new AtomicInteger(0);
            AtomicInteger sheetCounter = new AtomicInteger(0);
            AtomicReference<Sheet> currentSheet = new AtomicReference<>(sheet);

            employeeFlux
                    .parallel()
                    .subscribe( employee -> {
                        if (rowIndex.get() >= MAX_EXCEL_ROW_LIMIT) {
                            Sheet newSheet = workbook.createSheet(SHEET_TITLE + "-" + sheetCounter.incrementAndGet());
                            createHeaders(newSheet);
                            currentSheet.set(newSheet);
                            rowIndex.set(0);
                        }
                        Row row = currentSheet.get().createRow(rowIndex.incrementAndGet());
                        row.createCell(0).setCellValue(employee.getFirstName());
                        row.createCell(1).setCellValue(employee.getLastName());
                        row.createCell(2).setCellValue(employee.getFullName());
                        row.createCell(3).setCellValue(employee.getUserName());
                        row.createCell(4).setCellValue(employee.getTitle());
                        row.createCell(5).setCellValue(employee.getBloodGroup());
                    });

            workbook.write(baos);
            return Optional.of(new ByteArrayInputStream(baos.toByteArray()));
        } catch (IOException e) {
            log.error("Error exporting Excel", e);
            return Optional.empty();
        }
    }

    private void createHeaders(final Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("First Name");
        headerRow.createCell(1).setCellValue("Last Name");
        headerRow.createCell(2).setCellValue("Full Name");
        headerRow.createCell(3).setCellValue("User Name");
        headerRow.createCell(4).setCellValue("Title");
        headerRow.createCell(5).setCellValue("Blood Group");
    }
}
