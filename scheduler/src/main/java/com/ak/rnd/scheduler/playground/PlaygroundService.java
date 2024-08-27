package com.ak.rnd.scheduler.playground;

import com.ak.rnd.scheduler.config.TimerInfo;
import com.ak.rnd.scheduler.jobs.HelloWorldJob;
import com.ak.rnd.scheduler.timerservice.SchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaygroundService {
    private final SchedulerService schedulerService;

    public void runHelloWorldJob() {
        final TimerInfo timerInfo = TimerInfo.builder()
                .totalFireCount(5)
                .repeatIntervalMs(5000)
                .initialOffsetMs(1000)
                .callbackData("My Callback")
                .build();
        schedulerService.schedule(HelloWorldJob.class,timerInfo);
    }
}
