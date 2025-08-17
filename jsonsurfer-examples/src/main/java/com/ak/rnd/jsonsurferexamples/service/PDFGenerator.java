package com.ak.rnd.jsonsurferexamples.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.AreaBreakType;
import lombok.extern.slf4j.Slf4j;
import org.jsfr.json.JsonSurfer;
import org.jsfr.json.JsonSurferJackson;
import org.jsfr.json.SurfingConfiguration;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Slf4j
@Service("pdfGenerator")
public class PDFGenerator implements Generator {
    
    private static final int ROWS_PER_TABLE = 30; // Reduced for better page layout - one table per page
    private static final float MARGIN = 36f; // 0.5 inch margins
    
    // Page numbering event handler - first pass (just page number)
    private static class PageNumberEventHandler implements IEventHandler {
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfCanvas canvas = new PdfCanvas(docEvent.getPage().newContentStreamBefore(), 
                                           docEvent.getPage().getResources(), pdfDoc);
            
            Rectangle pageSize = docEvent.getPage().getPageSize();
            int pageNumber = pdfDoc.getPageNumber(docEvent.getPage());
            
            try {
                canvas.beginText()
                      .setFontAndSize(PdfFontFactory.createFont(StandardFonts.HELVETICA), 10)
                      .moveText(pageSize.getWidth() / 2 - 15, 20) // Bottom center
                      .showText("Page " + pageNumber)
                      .endText()
                      .release();
            } catch (Exception e) {
                // Ignore font errors
                canvas.release();
            }
        }
    }
    
    @Override
    public void generate(InputStream jsonInputStream, OutputStream outputStream) throws Exception {
        long startTime = System.currentTimeMillis();
        JsonSurfer surfer = JsonSurferJackson.INSTANCE;
        
        // Initialize PDF document with memory-efficient settings
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        
        // Add page numbering event handler
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new PageNumberEventHandler());
        
        Document document = new Document(pdfDoc);
        document.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
        
        // Data holders for single-pass processing
        List<String> headerLines = new ArrayList<>();
        List<String> configLines = new ArrayList<>();
        List<String> entityLines = new ArrayList<>();
        List<String> columns = new ArrayList<>();
        AtomicInteger rowCount = new AtomicInteger(0);
        AtomicInteger tableCount = new AtomicInteger(0);
        
        // Current table reference for memory-efficient table creation
        final Table[] currentTable = {null};
        
        try {
            // Single pass: collect all data and process rows immediately
            SurfingConfiguration config = SurfingConfiguration.builder()
                    .bind("$.headerInfo.rows", (value, context) -> {
                        try {
                            log.info("Found headerInfo rows: {}", value.getClass().getSimpleName());
                            com.fasterxml.jackson.databind.node.ObjectNode headerNode = 
                                (com.fasterxml.jackson.databind.node.ObjectNode) value;
                            
                            headerNode.fields().forEachRemaining(entry -> {
                                String key = entry.getKey();
                                String val = entry.getValue().asText();
                                String headerLine = key + ": " + val;
                                headerLines.add(headerLine);
                                log.info("Collected header line: {}", headerLine);
                            });
                        } catch (Exception e) {
                            log.error("Error processing headerInfo: " + e.getMessage(), e);
                        }
                    })
                    .bind("$.configInfo.rows", (value, context) -> {
                        try {
                            log.info("Found configInfo rows: {}", value.getClass().getSimpleName());
                            com.fasterxml.jackson.databind.node.ObjectNode configNode = 
                                (com.fasterxml.jackson.databind.node.ObjectNode) value;
                            
                            configNode.fields().forEachRemaining(entry -> {
                                String key = entry.getKey();
                                String val = entry.getValue().asText();
                                String configLine = key + ": " + val;
                                configLines.add(configLine);
                                log.info("Collected config line: {}", configLine);
                            });
                        } catch (Exception e) {
                            log.error("Error processing configInfo: " + e.getMessage(), e);
                        }
                    })
                    .bind("$.entityInfo.rows", (value, context) -> {
                        try {
                            log.info("Found entityInfo rows: {}", value.getClass().getSimpleName());
                            com.fasterxml.jackson.databind.node.ObjectNode entityNode = 
                                (com.fasterxml.jackson.databind.node.ObjectNode) value;
                            
                            entityNode.fields().forEachRemaining(entry -> {
                                String key = entry.getKey();
                                String val = entry.getValue().asText();
                                String entityLine = key + ": " + val;
                                entityLines.add(entityLine);
                                log.info("Collected entity line: {}", entityLine);
                            });
                        } catch (Exception e) {
                            log.error("Error processing entityInfo: " + e.getMessage(), e);
                        }
                    })
                    .bind("$.gridInfo.columns[*]", (value, context) -> {
                        String columnName = value.toString();
                        if (columnName.startsWith("\"") && columnName.endsWith("\"")) {
                            columnName = columnName.substring(1, columnName.length() - 1);
                        }
                        columns.add(columnName);
                        log.info("Collected column: {}", columnName);
                    })
                    .bind("$.gridInfo.rows[*]", (value, context) -> {
                        try {
                            // Write header sections only once when we encounter the first data row
                            if (rowCount.get() == 0) {
                                writeHeaderSection(document, headerLines);
                                writeConfigEntitySection(document, configLines, entityLines);
                                addSpacing(document, 2);
                            }
                            
                            // Create new table every ROWS_PER_TABLE rows for memory efficiency and page layout
                            if (rowCount.get() % ROWS_PER_TABLE == 0) {
                                if (currentTable[0] != null) {
                                    document.add(currentTable[0]);
                                    currentTable[0] = null; // Release memory
                                    log.info("Added table {} to document", tableCount.get());
                                    
                                    // Add page break after each table to ensure one table per page
                                    document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                                }
                                currentTable[0] = createNewTable(columns);
                                tableCount.incrementAndGet();
                                log.info("Created new table {} for one-table-per-page layout", tableCount.get());
                            }
                            
                            // Process and add data row immediately
                            com.fasterxml.jackson.databind.node.ObjectNode rowNode = 
                                (com.fasterxml.jackson.databind.node.ObjectNode) value;
                            
                            for (int i = 0; i < columns.size(); i++) {
                                String columnName = columns.get(i);
                                com.fasterxml.jackson.databind.JsonNode fieldNode = rowNode.get(columnName);
                                String fieldValue = fieldNode != null && !fieldNode.isNull() ? fieldNode.asText() : "";
                                
                                Cell cell = new Cell().add(new Paragraph(fieldValue));
                                currentTable[0].addCell(cell);
                            }
                            
                            int currentCount = rowCount.incrementAndGet();
                            if (currentCount % 1000 == 0) {
                                log.info("Processed {} rows", currentCount);
                            }
                            
                        } catch (Exception e) {
                            log.error("Error processing row: " + e.getMessage(), e);
                        }
                    })
                    .build();
            
            // Single pass through the JSON
            surfer.surf(jsonInputStream, config);
            
            // Add the last table if it exists
            if (currentTable[0] != null) {
                document.add(currentTable[0]);
                log.info("Added final table {} to document", tableCount.get());
            }
            
            // If no data rows were found, still write the header sections
            if (rowCount.get() == 0) {
                writeHeaderSection(document, headerLines);
                writeConfigEntitySection(document, configLines, entityLines);
                if (!columns.isEmpty()) {
                    addSpacing(document, 2);
                    Table emptyTable = createNewTable(columns);
                    document.add(emptyTable);
                }
            }
            
            // Second pass: Update page numbers with total count before closing
            updatePageNumbersWithTotal(pdfDoc);
            
            document.close();
            
            long totalTime = System.currentTimeMillis() - startTime;
            log.info("Single-pass PDF generation complete - Total rows: {}, Tables created: {}, Total time: {}ms", 
                    rowCount.get(), tableCount.get(), totalTime);
                    
        } catch (Exception e) {
            log.error("Error during single-pass PDF generation: " + e.getMessage(), e);
            throw new RuntimeException("PDF generation failed", e);
        }
    }

    @Override
    public void generate(Supplier<InputStream> inputStreamSupplier, OutputStream outputStream) throws Exception {
        // do nothing! - as requested
    }
    
    // Helper methods for PDF generation
    private void writeHeaderSection(Document document, List<String> headerLines) {
        try {
            for (String headerLine : headerLines) {
                Paragraph p = new Paragraph(headerLine)
                    .setFontSize(12)
                    .setBold()
                    .setTextAlignment(TextAlignment.LEFT);
                document.add(p);
                log.info("Written header line: {}", headerLine);
            }
            addSpacing(document, 2);
            log.info("Header section written with {} lines", headerLines.size());
        } catch (Exception e) {
            log.error("Error writing header section: " + e.getMessage(), e);
        }
    }
    
    private void writeConfigEntitySection(Document document, List<String> configLines, List<String> entityLines) {
        try {
            // Create a 2-column table for config and entity info
            Table infoTable = new Table(UnitValue.createPercentArray(new float[]{45, 10, 45}))
                .setWidth(UnitValue.createPercentValue(100));
            
            int maxRows = Math.max(configLines.size(), entityLines.size());
            for (int i = 0; i < maxRows; i++) {
                String configValue = i < configLines.size() ? configLines.get(i) : "";
                String entityValue = i < entityLines.size() ? entityLines.get(i) : "";
                
                infoTable.addCell(new Cell().add(new Paragraph(configValue)).setFontSize(10));
                infoTable.addCell(new Cell().add(new Paragraph(""))); // Spacer column
                infoTable.addCell(new Cell().add(new Paragraph(entityValue)).setFontSize(10));
                
                log.info("Written side-by-side row {}: '{}' | '{}'", i + 1, configValue, entityValue);
            }
            
            document.add(infoTable);
            addSpacing(document, 2);
            log.info("Config and entity section written");
        } catch (Exception e) {
            log.error("Error writing config/entity section: " + e.getMessage(), e);
        }
    }
    
    private Table createNewTable(List<String> columns) {
        if (columns.isEmpty()) {
            return null;
        }
        
        // Create table with equal column widths
        float[] columnWidths = new float[columns.size()];
        for (int i = 0; i < columns.size(); i++) {
            columnWidths[i] = 100f / columns.size(); // Equal width distribution
        }
        
        Table table = new Table(UnitValue.createPercentArray(columnWidths))
            .setWidth(UnitValue.createPercentValue(100));
        
        // Add header row
        for (String column : columns) {
            Cell headerCell = new Cell()
                .add(new Paragraph(column))
                .setFontSize(10)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER);
            table.addHeaderCell(headerCell);
        }
        
        log.info("Created new table with {} columns: {}", columns.size(), columns);
        return table;
    }
    
    private void addSpacing(Document document, int lines) {
        for (int i = 0; i < lines; i++) {
            document.add(new Paragraph(" ").setFontSize(6));
        }
    }
    
    private void updatePageNumbersWithTotal(PdfDocument pdfDoc) {
        try {
            int totalPages = pdfDoc.getNumberOfPages();
            
            // Update each page with the correct "Page X of Y" format
            for (int i = 1; i <= totalPages; i++) {
                PdfCanvas canvas = new PdfCanvas(pdfDoc.getPage(i).newContentStreamAfter(), 
                                               pdfDoc.getPage(i).getResources(), pdfDoc);
                
                Rectangle pageSize = pdfDoc.getPage(i).getPageSize();
                String pageText = " of " + totalPages;
                
                // Add the " of Y" part after the existing "Page X"
                canvas.beginText()
                      .setFontAndSize(PdfFontFactory.createFont(StandardFonts.HELVETICA), 10)
                      .moveText(pageSize.getWidth() / 2 + 15, 20) // Position after "Page X"
                      .showText(pageText)
                      .endText()
                      .release();
            }
            
            log.info("Updated page numbers with total count: {} pages", totalPages);
        } catch (Exception e) {
            log.error("Error updating page numbers with total: " + e.getMessage(), e);
            // Don't fail the entire PDF generation
        }
    }

}
