package com.rnd.app.dpstrategy.controller;

import com.rnd.app.dpstrategy.model.OutputType;
import com.rnd.app.dpstrategy.service.DocumentService;
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
