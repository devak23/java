package com.ak.rnd.springevents.handler;

import com.ak.rnd.springevents.event.PatientDischargeEvent;
import com.ak.rnd.springevents.service.IProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static com.ak.rnd.springevents.util.Utils.simulateDelay;

@Component
@Slf4j
public class HousekeepingHandler implements IProcess {

    @Override
    @EventListener
    @Async
    public void execute(PatientDischargeEvent event) {
        log.info("Processing Housekeeping for patient: {}", event.getPerson().getFirstName());
        simulateDelay();
        log.info("Done processing Housekeeping for patient: {}", event.getPerson().getFirstName());
    }
}
