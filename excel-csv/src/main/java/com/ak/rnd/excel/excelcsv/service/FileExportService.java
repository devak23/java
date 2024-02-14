package com.ak.rnd.excel.excelcsv.service;

import com.ak.rnd.excel.excelcsv.config.AppConfig;
import com.ak.rnd.excel.excelcsv.dao.DataProvider;
import com.ak.rnd.excel.excelcsv.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.ByteArrayInputStream;
import java.util.Optional;

@Service
public class FileExportService {
    @Autowired
    private ExcelExporter excelExporter;
    @Autowired
    private CsvExporter csvExporter;
    @Autowired
    private DataProvider dataProvider;
    @Autowired
    private AppConfig appConfig;

    public Optional<ByteArrayInputStream> exportToExcel(String key, int count) {
        Flux<?> fluxOfItems = dataProvider.getData(key, count);
        return excelExporter.downloadDataToExcel(key, fluxOfItems, appConfig.getModelMap(key));
    }

    public Optional<ByteArrayInputStream> exportToCSV(String key, int count) {
        Flux<?> fluxOfItems = dataProvider.getData(key, count);
        return csvExporter.downloadDataToCsv(fluxOfItems, appConfig.getModelMap(key));
    }
}
