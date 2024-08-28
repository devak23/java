package com.ak.rnd.scheduler.timerservice;

import com.ak.rnd.scheduler.config.TimerInfo;
import com.ak.rnd.scheduler.util.TimerUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
            scheduler.getListenerManager().addTriggerListener(new SimpleTriggerListener(this));
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

    public <T> void schedule(final Class<? extends Job> jobClass, final TimerInfo<T> timerInfo) {
        final JobDetail jobDetail = TimerUtils.buildJobDetail(jobClass, timerInfo);
        final Trigger trigger = TimerUtils.buildTrigger(jobClass, timerInfo);

        try {
            log.info("Running the job: {}", jobDetail.getKey());
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            log.error("Error scheduling job", e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<TimerInfo<T>> getAllRunningTimers() {
        try {
            return scheduler.getJobKeys(GroupMatcher.anyGroup())
                    .stream()
                    .map(jobKey -> {
                        try {
                            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                            return (TimerInfo<T>) jobDetail.getJobDataMap().get(jobKey.getName());
                        } catch (SchedulerException e) {
                            log.error("Error getting job detail", e);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } catch (SchedulerException e) {
            log.error("Error getting all running timers", e);
            return Collections.emptyList();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> TimerInfo<T> getRunningTimer(String timerId) {
        try {
            JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
            if (jobDetail == null) {
                return null;
            }

            return (TimerInfo<T>) jobDetail.getJobDataMap().get(timerId);
        } catch (SchedulerException e) {
            log.error("Error getting job detail for timerId: {}", timerId, e);
            return null;
        }
    }

    public <T> void updateTimer(final String timerId, final TimerInfo<T> timerInfo) {
        try {
            JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
            if (jobDetail != null) {
                log.info("Updating timer({}) with timerInfo: {}", timerId, timerInfo);
                jobDetail.getJobDataMap().put(timerId, timerInfo);
            }
        } catch (SchedulerException e) {
            log.error("Error getting job detail for timerId: {}", timerId, e);
        }
    }

    public boolean deleteTimer(final String timerId) {
        try {
            return scheduler.deleteJob(new JobKey(timerId));
        } catch (SchedulerException e) {
            log.error("Error deleting job detail for timerId: {}", timerId, e);
        }
        return false;
    }
}
