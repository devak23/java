package com.example.streaming;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
public class StreamingPdfController {

    @GetMapping("/stream-pdf")
    public ResponseEntity<StreamingResponseBody> streamPdf() {

        StreamingResponseBody stream = outputStream -> {
            try {
                // Create PDF writer that writes directly to the output stream
                PdfWriter writer = new PdfWriter(outputStream);
                PdfDocument pdfDocument = new PdfDocument(writer);
                Document document = new Document(pdfDocument);

                // Add title
                document.add(new Paragraph("Streaming PDF Report")
                        .setFontSize(20)
                        .setBold());

                // Simulate streaming data processing
                for (int i = 1; i <= 10; i++) {
                    // Add content section
                    document.add(new Paragraph("Section " + i)
                            .setFontSize(16)
                            .setBold());

                    // Create a table for this section
                    Table table = new Table(UnitValue.createPercentArray(3))
                            .setWidth(UnitValue.createPercentValue(100));

                    // Add table headers
                    table.addHeaderCell("ID");
                    table.addHeaderCell("Name");
                    table.addHeaderCell("Value");

                    // Simulate data fetching and add rows
                    for (int j = 1; j <= 5; j++) {
                        table.addCell(String.valueOf((i - 1) * 5 + j));
                        table.addCell("Item " + j);
                        table.addCell("Value " + (i * j));
                    }

                    document.add(table);
                    document.add(new Paragraph("\n"));

                    // Force flush to send current content to client
                    document.flush();

                    // Simulate processing delay (remove in production)
                    Thread.sleep(500);
                }

                // Close the document
                document.close();

            } catch (Exception e) {
                throw new RuntimeException("Error generating PDF", e);
            }
        };

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=streaming-report.pdf")
                .body(stream);
    }

    @GetMapping("/stream-pdf-with-data")
    public ResponseEntity<StreamingResponseBody> streamPdfWithData() {

        StreamingResponseBody stream = outputStream -> {
            try {
                PdfWriter writer = new PdfWriter(outputStream);
                PdfDocument pdfDocument = new PdfDocument(writer);
                Document document = new Document(pdfDocument);

                document.add(new Paragraph("Real-time Data Report")
                        .setFontSize(20)
                        .setBold());

                // Simulate real-time data streaming
                DataStreamSimulator dataStream = new DataStreamSimulator();

                while (dataStream.hasNext()) {
                    List<DataRecord> batch = dataStream.getNextBatch();

                    if (!batch.isEmpty()) {
                        // Add batch header
                        document.add(new Paragraph("Batch " + dataStream.getBatchNumber())
                                .setFontSize(14)
                                .setBold());

                        // Create table for batch
                        Table table = new Table(UnitValue.createPercentArray(4))
                                .setWidth(UnitValue.createPercentValue(100));

                        table.addHeaderCell("Timestamp");
                        table.addHeaderCell("ID");
                        table.addHeaderCell("Description");
                        table.addHeaderCell("Amount");

                        for (DataRecord record : batch) {
                            table.addCell(record.getTimestamp());
                            table.addCell(record.getId());
                            table.addCell(record.getDescription());
                            table.addCell(String.valueOf(record.getAmount()));
                        }

                        document.add(table);
                        document.add(new Paragraph("\n"));

                        // Flush to send current content immediately
                        document.flush();
                    }
                }

                document.close();

            } catch (Exception e) {
                throw new RuntimeException("Error generating streaming PDF", e);
            }
        };

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=realtime-report.pdf")
                .body(stream);
    }

    // Alternative approach using direct servlet response
    @GetMapping("/stream-pdf-direct")
    public void streamPdfDirect(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=direct-stream.pdf");

        try (OutputStream outputStream = response.getOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Direct Stream PDF")
                    .setFontSize(20)
                    .setBold());

            // Add content in chunks
            for (int i = 1; i <= 5; i++) {
                document.add(new Paragraph("Processing chunk " + i + "...")
                        .setFontSize(12));

                // Add some data
                Table table = new Table(UnitValue.createPercentArray(2))
                        .setWidth(UnitValue.createPercentValue(100));

                for (int j = 1; j <= 3; j++) {
                    table.addCell("Key " + j);
                    table.addCell("Value " + (i * j));
                }

                document.add(table);
                document.add(new Paragraph("\n"));

                // Flush both document and servlet output stream
                document.flush();
                outputStream.flush();

                // Simulate processing time
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error in direct PDF streaming", e);
        }
    }
}
