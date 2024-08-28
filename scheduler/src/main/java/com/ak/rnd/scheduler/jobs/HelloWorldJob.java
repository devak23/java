package com.ak.rnd.scheduler.jobs;

import com.ak.rnd.scheduler.config.TimerInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
//~ 1!
public class HelloWorldJob implements Job {
    @Override
    @SuppressWarnings("unchecked")
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        TimerInfo<String> timerInfo = (TimerInfo<String>) jobDataMap.get(HelloWorldJob.class.getSimpleName());
        log.info(timerInfo.callbackData());
    }
}
