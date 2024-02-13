package com.ak.rnd.excel.excelcsv.controller;

import com.ak.rnd.excel.excelcsv.service.ExcelExportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/excel")
@Slf4j
public class ExcelExportController {
    @Autowired
    ExcelExportService excelExportService;
    @GetMapping
    public String status() {
        return "Ready for download!";
    }

    @GetMapping("/download")
    public void exportToExcel(HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        Optional<String> exportFile = excelExportService.exportToExcel();
        if (exportFile.isPresent()) {
            response.setHeader("Content-Disposition", "attachment; filename=" + exportFile);
        } else {
            log.error("Could not export to excel");
        }
    }
}
