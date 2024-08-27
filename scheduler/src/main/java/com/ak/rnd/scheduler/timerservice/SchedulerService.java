package com.ak.rnd.scheduler.timerservice;

import com.ak.rnd.scheduler.config.TimerInfo;
import com.ak.rnd.scheduler.util.TimerUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
//~ 2!
public class SchedulerService {
    private final Scheduler scheduler;

    @PostConstruct
    public void init() {
        log.info("starting scheduler...");
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            log.error("Error starting scheduler", e);
        }
    }

    @PreDestroy
    public void preDestroy() {
        log.info("shutting down scheduler...");
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            log.error("Error shutting down scheduler", e);
        }
    }

    public void schedule(final Class jobClass, final TimerInfo timerInfo) {
        final JobDetail jobDetail = TimerUtils.buildJobDetail(jobClass, timerInfo);
        final Trigger trigger = TimerUtils.buildTrigger(jobClass, timerInfo);

        try {
            log.info("Running the job: {}", jobDetail.getKey());
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            log.error("Error scheduling job", e);
        }
    }
}
