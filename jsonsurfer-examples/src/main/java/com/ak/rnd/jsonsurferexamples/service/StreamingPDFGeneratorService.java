package com.ak.rnd.jsonsurferexamples.service;

import com.ak.rnd.jsonsurferexamples.components.TransactionDataClient;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
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
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class StreamingPDFGeneratorService {
    private final String pdfStorageDirectory = "/tmp";
    private final TransactionDataClient transactionDataClient;
    private static final int ROWS_PER_PAGE = 17; // Optimal rows that fit in one page
    private static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("#,##0.00");

    public InputStream generatePDFStream(String requestId, String documentId) {
        Path pdfPath = null;

        try {
            // Create storage directory if it doesn't exist
            Path storagePath = Paths.get(pdfStorageDirectory);
            Files.createDirectories(storagePath);

            pdfPath = storagePath.resolve(documentId + ".pdf");

            log.info("Starting PDF generation for requestId: {} with documentId: {}", requestId, documentId);

            // Create PDF with iText 7 streaming approach
            try (FileOutputStream fos = new FileOutputStream(pdfPath.toFile())) {

                // Initialize PDF writer and document
                PdfWriter writer = new PdfWriter(fos);
                PdfDocument pdfDoc = new PdfDocument(writer);

                // Set page size to landscape A4
                pdfDoc.setDefaultPageSize(PageSize.A4.rotate());

                // Add page number event handler
                PageNumberHandler pageNumberHandler = new PageNumberHandler();
                pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, pageNumberHandler);

                // Create document with margins
                Document document = new Document(pdfDoc);
                document.setMargins(50, 40, 60, 40); // top, right, bottom, left - extra bottom for footer

                // Add title
                Paragraph title = new Paragraph("Transaction Report")
                        .setFontSize(18)
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setMarginBottom(15);
                document.add(title);

                // Add request info
                document.add(new Paragraph("Request ID: " + requestId).setFontSize(10));
                document.add(new Paragraph("Generated: " + LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .setFontSize(10)
                        .setMarginBottom(15));

                // Create table with 5 columns
                float[] columnWidths = {3, 2, 3, 2, 2};
                Table table = new Table(UnitValue.createPercentArray(columnWidths));
                table.setWidth(UnitValue.createPercentValue(100));

                // Make header repeat on each page
                table.setSkipFirstHeader(false);

                // Add headers
                addTableHeader(table);

                // Stream data from external service and add to PDF
                long recordCount = 0;
                int rowsSinceLastFlush = 0;

                try (Stream<Map<String, String>> dataStream = transactionDataClient.streamTransactionData(requestId)) {

                    for (Map<String, String> record : (Iterable<Map<String, String>>) dataStream::iterator) {
                        addTableRow(table, record);
                        recordCount++;
                        rowsSinceLastFlush++;

                        // Flush based on rows per page to maintain layout consistency
                        // Flush every 17 rows (one page worth) to keep tables continuous
                        if (rowsSinceLastFlush >= ROWS_PER_PAGE) {
                            document.add(table);

                            // Create new table for next page
                            table = new Table(UnitValue.createPercentArray(columnWidths));
                            table.setWidth(UnitValue.createPercentValue(100));
                            table.setSkipFirstHeader(false);

                            // Re-add headers
                            addTableHeader(table);

                            // Force flush to free memory
                            document.flush();

                            rowsSinceLastFlush = 0;

                            log.debug("Processed {} records", recordCount);
                        }
                    }

                    // Add remaining rows
                    if (table.getNumberOfRows() > 1) { // > 1 because header is row 0
                        document.add(table);
                    }
                }

                // Update total pages in the handler before closing
                pageNumberHandler.setTotalPages(pdfDoc.getNumberOfPages());

                // Add footer with total count on last page
                document.add(new Paragraph("\nTotal Records: " + NUMBER_FORMAT.format(recordCount))
                        .setFontSize(12)
                        .setBold()
                        .setMarginTop(15));

                // Close document (this also closes pdfDoc and writer)
                document.close();

                log.info("PDF generation completed for documentId: {}. Total records: {}", documentId, recordCount);
            }

            // Return input stream of generated PDF
            return new FileInputStream(pdfPath.toFile());

        } catch (Exception e) {
            log.error("Error generating PDF for documentId: {}", documentId, e);

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
        // Transaction Ref
        addCell(table, record.getOrDefault("transactionRef", ""), TextAlignment.LEFT);

        // Amount - formatted with comma separator
        String amount = record.getOrDefault("amount", "0");
        try {
            double amountValue = Double.parseDouble(amount);
            addCell(table, NUMBER_FORMAT.format(amountValue), TextAlignment.RIGHT);
        } catch (NumberFormatException e) {
            addCell(table, amount, TextAlignment.RIGHT);
        }

        // Bank
        addCell(table, record.getOrDefault("bank", ""), TextAlignment.LEFT);

        // Currency
        addCell(table, record.getOrDefault("currency", ""), TextAlignment.CENTER);

        // Branch Code
        addCell(table, record.getOrDefault("branchCode", ""), TextAlignment.CENTER);
    }

    private void addCell(Table table, String text, TextAlignment alignment) {
        Cell cell = new Cell()
                .add(new Paragraph(text).setFontSize(9))
                .setPadding(4)
                .setTextAlignment(alignment);
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


    /**
     * Event handler for adding page numbers in footer
     */
    private static class PageNumberHandler implements IEventHandler {
        private int totalPages = 0;

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNumber = pdfDoc.getPageNumber(page);

            Rectangle pageSize = page.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

            Canvas canvas = new Canvas(pdfCanvas, pageSize);

            // Calculate total pages if not set yet (will be set on document close)
            int total = totalPages > 0 ? totalPages : pdfDoc.getNumberOfPages();

            // Add page number in footer
            Paragraph footer = new Paragraph(String.format("Page %d of %d", pageNumber, total))
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER);

            // Position at bottom center of page
            canvas.showTextAligned(footer, pageSize.getWidth() / 2, 30, TextAlignment.CENTER);

            canvas.close();
        }
    }
}
