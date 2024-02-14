package com.ak.rnd.excel.excelcsv.service;

import com.ak.rnd.excel.excelcsv.config.LogTime;
import de.siegmar.fastcsv.writer.CsvWriter;
import de.siegmar.fastcsv.writer.LineDelimiter;
import de.siegmar.fastcsv.writer.QuoteStrategies;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@Slf4j
public class CsvExporter {
    @LogTime
    public Optional<ByteArrayInputStream> downloadDataToCsv(Flux<?> fluxOfItems, Map<String, String> headerMethodMap) {
        System.out.println(headerMethodMap);
        try (
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                final PrintWriter pw = new PrintWriter(baos,false, StandardCharsets.UTF_8);
                final CsvWriter csvWriter = CsvWriter
                        .builder()
                        .quoteStrategy(QuoteStrategies.ALWAYS)
                        .fieldSeparator(',')
                        .lineDelimiter(LineDelimiter.LF)
                        .build(pw)
        ) {
            csvWriter.writeRecord(headerMethodMap.values().toArray(new String[1]));
            fluxOfItems
                    .parallel()
                    .subscribe(item -> {
                        Field[] fields = item.getClass().getDeclaredFields();
                        Map<String, Field> nameFieldMap = createNameFieldMap(fields);
                        String[] values = new String[fields.length];
                        try {
                            int i = 0;
                            for (String key : headerMethodMap.keySet()) {
                                Field f = nameFieldMap.get(key);
                                f.setAccessible(true);
                                values[i++] = (String) f.get(item);
                            }
                            csvWriter.writeRecord(values);
                        } catch (Exception ex) {
                            throw new RuntimeException("could not get values", ex);
                        }
                    });
            pw.flush();
            return Optional.of(new ByteArrayInputStream(baos.toByteArray()));
        } catch (IOException e) {
            log.error("Error downloading Csv");
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
}
