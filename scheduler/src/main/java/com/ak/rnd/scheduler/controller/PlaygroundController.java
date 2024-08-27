package com.ak.rnd.scheduler.controller;

import com.ak.rnd.scheduler.playground.PlaygroundService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/timer")
@RequiredArgsConstructor
public class PlaygroundController {
    private final PlaygroundService playgroundService;

    @PostMapping("/runHelloWorld")
    public void runHelloWorldJob() {
        playgroundService.runHelloWorldJob();
    }
}
