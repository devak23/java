package com.ak.rnd.scheduler.controller;

import com.ak.rnd.scheduler.config.TimerInfo;
import com.ak.rnd.scheduler.playground.PlaygroundService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timer")
@RequiredArgsConstructor
public class PlaygroundController {
    private final PlaygroundService playgroundService;

    @PostMapping("/runHelloWorld")
    public void runHelloWorldJob() {
        playgroundService.runHelloWorldJob();
    }

    @GetMapping
    public <T> List<TimerInfo<T>> getAllTimers() {
        return playgroundService.getAllRunningTimers();
    }

    @GetMapping("/{timerId}")
    public <T> TimerInfo<T> getRunningTimer(@PathVariable final String timerId) {
        return playgroundService.getRunningTimer(timerId);
    }

    @DeleteMapping("/{timerId}")
    public boolean deleteTimer(@PathVariable final String timerId) {
        return playgroundService.deleteTimer(timerId);
    }
}
