package com.example.streaming.service.v1;

import com.example.streaming.model.DataRecord;
import com.example.streaming.model.DataStreamSimulator;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class PDFService {
    public void generatePDFDocumentWithData(OutputStream outputStream) {
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
    }

    public void generateGenericPDFDocument(OutputStream outputStream) throws InterruptedException {
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
    }

    public void streamPDFDirect(OutputStream outputStream) throws IOException {
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
    }
}
