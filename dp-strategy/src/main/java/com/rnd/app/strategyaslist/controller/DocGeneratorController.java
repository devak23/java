package com.rnd.app.strategyaslist.controller;

import com.rnd.app.strategyaslist.model.OutputType;
import com.rnd.app.strategyaslist.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/generate")
@RequiredArgsConstructor
public class DocGeneratorController {
    private final DocumentService documentService;

    @PostMapping("/{type}")
    public ResponseEntity<String> generateDoc(@PathVariable final String type) {
        return ResponseEntity.ok(documentService.create(OutputType.valueOf(type)));
    }
}
