package com.ak.rnd.excel.excelcsv.service;

import com.ak.rnd.excel.excelcsv.config.LogTime;
import com.ak.rnd.excel.excelcsv.model.Employee;
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
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
@Slf4j
public class CsvExporter {
    @LogTime
    public Optional<ByteArrayInputStream> downloadEmployeesToFile(Flux<Employee> employeeFlux) {
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
            csvWriter.writeRecord("First Name", "Last Name", "Full Name", "User Name", "Title", "Blood Group");
            employeeFlux
                    .parallel()
                    .subscribe(employee -> {
                        csvWriter.writeRecord(
                                employee.getFirstName(),
                                employee.getLastName(),
                                employee.getFullName(),
                                employee.getUserName(),
                                employee.getTitle(),
                                employee.getBloodGroup()
                        );
                    });
            pw.flush();
            return Optional.of(new ByteArrayInputStream(baos.toByteArray()));
        } catch (IOException e) {
            log.error("Error downloading Csv");
            return Optional.empty();
        }
    }
}
