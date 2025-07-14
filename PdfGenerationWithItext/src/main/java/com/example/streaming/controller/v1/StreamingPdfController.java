package com.example.streaming.controller.v1;

import com.example.streaming.service.v1.PDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/api/v1")
public class StreamingPdfController {
    @Autowired
    private PDFService pdfService;

    @GetMapping("/stream-pdf")
    public ResponseEntity<StreamingResponseBody> streamPdf() {
        StreamingResponseBody stream = outputStream -> {
            try {
                pdfService.generateGenericPDFDocument(outputStream);

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
                pdfService.generatePDFDocumentWithData(outputStream);
            } catch (Exception e) {
                throw new RuntimeException("Error generating streaming PDF", e);
            }
        };

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=realtime-report.pdf")
                .body(stream);
    }

    @GetMapping("/stream-pdf-direct")
    public void streamPdfDirect(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=direct-stream.pdf");

        try (OutputStream outputStream = response.getOutputStream()) {
            pdfService.streamPDFDirect(outputStream);
        } catch (Exception e) {
            throw new RuntimeException("Error in direct PDF streaming", e);
        }
    }
}
