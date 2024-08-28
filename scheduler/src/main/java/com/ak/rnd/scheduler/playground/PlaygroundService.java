package com.ak.rnd.scheduler.playground;

import com.ak.rnd.scheduler.config.TimerInfo;
import com.ak.rnd.scheduler.jobs.HelloWorldJob;
import com.ak.rnd.scheduler.timerservice.SchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaygroundService {
    private final SchedulerService schedulerService;

    public void runHelloWorldJob() {
        final TimerInfo<String> timerInfo = TimerInfo.<String>builder()
                .totalFireCount(5)
                .repeatIntervalMs(2000)
                .initialOffsetMs(1000)
                .callbackData("My Callback")
                .build();
        schedulerService.schedule(HelloWorldJob.class,timerInfo);
    }

    public <T> List<TimerInfo<T>> getAllRunningTimers() {
        return schedulerService.getAllRunningTimers();
    }

    public <T> TimerInfo<T> getRunningTimer(String timerId) {
        return schedulerService.getRunningTimer(timerId);
    }
}
