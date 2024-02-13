package com.ak.rnd.excel.excelcsv.controller;

import com.ak.rnd.excel.excelcsv.service.ExcelExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/excel")
@Slf4j
public class ExcelExportController {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Autowired
    ExcelExportService excelExportService;
    @GetMapping
    public String status() {
        return "Ready for download!";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> exportToExcel() {
        String filename = "employee-" + DATE_TIME_FORMATTER.format(LocalDateTime.now()) + ".xlsx";

        Optional<ByteArrayInputStream> exportFile = excelExportService.exportToExcel(100);
        if (exportFile.isPresent()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(new InputStreamResource(exportFile.get()));
        } else {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
