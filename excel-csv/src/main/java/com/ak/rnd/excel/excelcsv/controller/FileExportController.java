package com.ak.rnd.excel.excelcsv.controller;

import com.ak.rnd.excel.excelcsv.service.FileExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.ak.rnd.excel.excelcsv.utils.Constants.*;
import static com.ak.rnd.excel.excelcsv.utils.Constants.DATE_TIME_FORMATTER;

@RestController
@RequestMapping(value = {"/download","/download/"})
@Slf4j
public class FileExportController {
    @Autowired
    FileExportService fileExportService;
    @GetMapping
    public String status() {
        return "ExcelExporter is ready for download!";
    }

    @GetMapping("/excel/{count}")
    public ResponseEntity<?> downloadExcel(@PathVariable("count") int count) {
        String filename = FILE_NAME + DATE_TIME_FORMATTER.format(LocalDateTime.now()) + EXCEL_XTN;

        if (count <= 0) {
            return ResponseEntity.internalServerError().body("Nothing to download!");
        }

        Optional<ByteArrayInputStream> bais = fileExportService.exportToExcel(count);
        if (bais.isPresent()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(new InputStreamResource(bais.get()));
        } else {
            return ResponseEntity.internalServerError().body("Error creating Excel file.");
        }
    }

    @GetMapping("/csv/{count}")
    public ResponseEntity<?> downloadCsv(@PathVariable("count") int count) {
        String filename = FILE_NAME + DATE_TIME_FORMATTER.format(LocalDateTime.now()) + CSV_XTN;

        if (count <= 0) {
            return ResponseEntity.internalServerError().body("Nothing to download!");
        }

        Optional<ByteArrayInputStream> bais = fileExportService.exportToCSV(count);
        if (bais.isPresent()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/text"))
                    .body(new InputStreamResource(bais.get()));
        } else {
            return ResponseEntity.internalServerError().body("Error creating Csv file.");
        }
    }
}
