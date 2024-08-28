package com.rnd.app.strategyasmap.controller;

import com.rnd.app.strategyasmap.config.NotificationServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notify")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationServiceFactory factory;

    @PostMapping("/{type}")
    public ResponseEntity<String> notify(@PathVariable("type") final String type) {
        return ResponseEntity.ok(factory.sendNotification(type));
    }
}
