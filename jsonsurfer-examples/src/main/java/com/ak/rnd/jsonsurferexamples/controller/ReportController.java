package com.ak.rnd.jsonsurferexamples.controller;

import com.ak.rnd.jsonsurferexamples.model.GridExportRequest;
import com.ak.rnd.jsonsurferexamples.model.GridExportResponse;
import com.ak.rnd.jsonsurferexamples.service.StreamingPDFGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/reports")
public class ReportController {
    private final StreamingPDFGeneratorService streamingPdfGeneratorService;

    @Qualifier("pdfGeneratorThread")
    private final Executor pdfExecutor;

    @PostMapping("/generate")
    public ResponseEntity<?> generateReport(@RequestBody GridExportRequest request) throws IOException {
        log.info("Generating report for request: {}", request);
        // Let's create a unique documentId that client will use to fetch the data
        String documentId = UUID.randomUUID().toString();

        // We will now try to generate the report and stream it to the client
        CompletableFuture<InputStream> pdfFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return streamingPdfGeneratorService.generatePDFStream(request.getRequestId(), documentId);
            } catch (Exception e) {
                log.error("Error generating PDF for requestId: {}, documentId: {}", request.getRequestId(), documentId, e);
                return null;
            }
        }, pdfExecutor);


        // This call will be blocked until the future is completed or the timeout is reached
        try {
            InputStream pdfStream = pdfFuture.get(request.getTimeoutSeconds(), TimeUnit.SECONDS);
            log.info("PDF generated successfully within the SLA for documentId: {}. Streaming to the client.", documentId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "report_" + documentId + ".pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            InputStreamResource resource = new InputStreamResource(pdfStream);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);

        } catch (TimeoutException e) {
            String messageToClient = "Failed to generate report";
            messageToClient = "Report generation exceeded stipulated time. It will be emailed to the client directly.";
            // when the timeout is reached, we will send a message instead of streaming the file.
            log.error("Either report generation exceeded stipulated time or there was an error: ", e);

            pdfFuture.whenComplete((stream, error) -> {
                if (error != null) {
                    log.error("Error generating report: ", error);
                } else {
                    log.info("PDF generated successfully in background for documentId: {}", documentId);
                    // Close the stream since we're not using it
                    try {
                        if (stream != null) stream.close();
                    } catch (Exception ex) {
                        log.warn("Error closing stream", ex);
                    }
                }
            });

            // in either case (Exception or Timeout) we are going to return the GridExportResponse
            GridExportResponse response = GridExportResponse.builder()
                    .requestId(request.getRequestId())
                    .documentId(documentId)
                    .message(messageToClient)
                    .status("PROCESSING")
                    .build();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (Exception e) {
            log.error("Error generating report: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(GridExportResponse.builder()
                            .message("Error generating report")
                            .status("ERROR")
                            .build());
        }
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<?> downloadReport(@PathVariable String documentId) {
        log.info("Retrieving report for documentId: {}", documentId);

        try {
            InputStream pdfStream = streamingPdfGeneratorService.retrieveGeneratedPDF(documentId);

            if (pdfStream == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(GridExportResponse.builder()
                                .message("Report not found or still being generated")
                                .status("NOT_FOUND")
                                .build());
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=report_" + documentId + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(pdfStream));

        } catch (Exception e) {
            log.error("Error retrieving report", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(GridExportResponse.builder()
                            .message("Error retrieving report")
                            .status("ERROR")
                            .build());
        }
    }
}
