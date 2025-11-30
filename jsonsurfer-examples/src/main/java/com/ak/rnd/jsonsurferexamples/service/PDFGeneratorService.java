package com.ak.rnd.jsonsurferexamples.service;

import com.ak.rnd.jsonsurferexamples.components.TransactionDataClient;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PDFGeneratorService {
    private final String pdfStorageDirectory = "/tmp";
    private final TransactionDataClient transactionDataClient;

    public InputStream generatePDFStream(String requestId, String documentId) {
        // Let's store the generated PDF in a temporary file or a database
        Path storagePath = Paths.get(pdfStorageDirectory);
        Path pdfPath = storagePath.resolve(documentId + ".pdf");

        try {
            Files.createDirectories(storagePath);

            log.info("Starting PDF generation for requestId: {}, documentId: {}", requestId, documentId);

            // Create a PDF with streaming
            try (FileOutputStream fos = new FileOutputStream(pdfPath.toFile())) {
                PdfWriter writer = new PdfWriter(fos);
                PdfDocument pdfDoc = new PdfDocument(writer);
                pdfDoc.setDefaultPageSize(PageSize.A4.rotate());
                Document document = new Document(pdfDoc);

                // Create title
                Paragraph title = new Paragraph("Transaction Report")
                        .setFontSize(18)
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setMarginBottom(20);
                document.add(title);
                log.info("Added title to document");

                // Add request info
                document.add(new Paragraph("Request ID: " + requestId).setFontSize(12).setMarginBottom(10));
                document.add(new Paragraph("Generated on: " + LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).setFontSize(10));
                log.info("Added request info to document");

                // Create table with 5 columns
                Table table = new Table(UnitValue.createPercentArray(new float[]{20, 20, 20, 20, 20}))
                        .setWidth(UnitValue.createPercentValue(100));
                document.add(table);
                log.info("Added table to document");

                // Add headers
                addTableHeader(table);

                long recordCount = 0;
                int batchSize = 100; // We will flush every 100 records

                try (Stream<Map<String, String>> records = transactionDataClient.streamTransactionData(requestId)) {
                    for (Map<String, String> record : (Iterable<Map<String, String>>) records::iterator) {
                        addTableRow(table, record);
                        recordCount++;

                        // flush the table after every 100 rows
                        if (recordCount % batchSize == 0) {
                            document.add(table);

                            // Create a new table for next batch
                            table = new Table(UnitValue.createPercentArray(new float[]{3, 2, 3, 2, 2}))
                                    .setWidth(UnitValue.createPercentValue(100));

                            // Re-add the table headers for continuation
                            addTableHeader(table);
                            document.flush();
                            log.info("Flushed {} records and started new table", recordCount);
                        }
                    }
                    if (table.getNumberOfRows() > 1) { // 1 because header is also a row
                        document.add(table);
                    }
                    log.info("Finished processing {} records", recordCount);
                }

                // add footer
                document.add(new Paragraph("\nTotal Records: " + recordCount)
                        .setFontSize(12)
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setMarginTop(10));

                document.close();
                log.info("Finished PDF generation for requestId: {}, documentId: {}", requestId, documentId);

                return new FileInputStream(pdfPath.toFile());
            }
        } catch (Exception e) {
            log.error("Error generating PDF for requestId: {}, documentId: {}", requestId, documentId, e);

            // Clean up partial file on error
            if (pdfPath != null) {
                try {
                    Files.deleteIfExists(pdfPath);
                } catch (IOException ioException) {
                    log.warn("Failed to delete partial PDF file: {}", pdfPath, ioException);
                }
            }

            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    private void addTableHeader(Table table) {
        String[] headers = {"Transaction Ref", "Amount", "Bank", "Currency", "Branch Code"};

        for (String header : headers) {
            Cell cell = new Cell()
                    .add(new Paragraph(header)
                            .setFontSize(10)
                            .setBold())
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPadding(5);
            table.addHeaderCell(cell);
        }
    }

    private void addTableRow(Table table, Map<String, String> record) {
        addCell(table, record.getOrDefault("transactionRef", ""));
        addCell(table, record.getOrDefault("amount", ""));
        addCell(table, record.getOrDefault("bank", ""));
        addCell(table, record.getOrDefault("currency", ""));
        addCell(table, record.getOrDefault("branchCode", ""));
    }

    private void addCell(Table table, String text) {
        Cell cell = new Cell()
                .add(new Paragraph(text).setFontSize(9))
                .setPadding(4);
        table.addCell(cell);
    }


    public InputStream retrieveGeneratedPDF(String documentId) {
        try {
            Path pdfPath = Paths.get(pdfStorageDirectory, documentId + ".pdf");

            if (Files.exists(pdfPath)) {
                return new FileInputStream(pdfPath.toFile());
            }

            log.warn("PDF not found for documentId: {}", documentId);
            return null;

        } catch (Exception e) {
            log.error("Error retrieving PDF for documentId: {}", documentId, e);
            return null;
        }
    }

}
