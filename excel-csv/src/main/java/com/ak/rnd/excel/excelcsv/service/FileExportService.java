package com.ak.rnd.excel.excelcsv.service;

import com.ak.rnd.excel.excelcsv.dao.EmployeeDAO;
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
    private EmployeeDAO employeeDAO;

    public Optional<ByteArrayInputStream> exportToExcel(int count) {
        Flux<Employee> employeeFlux = employeeDAO.getEmployees(count);
        return excelExporter.downloadEmployeesToFile(employeeFlux);
    }

    public Optional<ByteArrayInputStream> exportToCSV(int count) {
        Flux<Employee> employeeFlux = employeeDAO.getEmployees(count);
        return csvExporter.downloadEmployeesToFile(employeeFlux);
    }
}
