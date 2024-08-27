package com.ak.rnd.scheduler.util;

import com.ak.rnd.scheduler.config.TimerInfo;
import org.quartz.*;

import java.util.Date;

//~ 4!
public final class TimerUtils {
    private TimerUtils() {}

    //~ 5!
    public static JobDetail buildJobDetail(final Class<? extends Job> jobClass, final TimerInfo timerInfo) {
        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobClass.getSimpleName(), timerInfo);

        return JobBuilder
                .newJob(jobClass)
                .withIdentity(jobClass.getSimpleName())
                .setJobData(jobDataMap)
                .build();
    }

    //~ 6!
    public static Trigger buildTrigger(final Class<? extends Job> jobClass, final TimerInfo timerInfo) {
        SimpleScheduleBuilder builder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInMilliseconds(timerInfo.getRepeatIntervalMs());

        if (timerInfo.isRunForever()) {
            builder = builder.repeatForever();
        } else {
            builder = builder.withRepeatCount(timerInfo.getTotalFireCount() - 1);
        }

        return TriggerBuilder.newTrigger()
                .withIdentity(jobClass.getSimpleName())
                .withSchedule(builder)
                .startAt(new Date(System.currentTimeMillis() + timerInfo.getInitialOffsetMs()))
                .build();
    }
}
