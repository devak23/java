package com.ak.rnd.scheduler.timerservice;


import com.ak.rnd.scheduler.config.TimerInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

@Slf4j
public class SimpleTriggerListener implements TriggerListener {
    private SchedulerService schedulerService;

    public SimpleTriggerListener(final SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Override
    public String getName() {
        return SimpleTriggerListener.class.getSimpleName();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
        final String timerId = trigger.getKey().getName();
        final JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        final TimerInfo<String> timerInfo = (TimerInfo<String>) jobDataMap.get(timerId);
        log.info("Trigger fired for {}", timerInfo);

        if (timerInfo != null && !timerInfo.isRunForever()) {
            int remainingFireCount = timerInfo.getRemainingFireCount();

            if (remainingFireCount > 0) {
                timerInfo.setRemainingFireCount(remainingFireCount - 1);
            }
        }
        log.info("New timerInfo = {}", timerInfo);
        schedulerService.updateTimer(timerId, timerInfo);
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {

    }
}
