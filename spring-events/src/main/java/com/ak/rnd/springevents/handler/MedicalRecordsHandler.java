package com.ak.rnd.springevents.handler;

import com.ak.rnd.springevents.event.PatientDischargeEvent;
import com.ak.rnd.springevents.service.IProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static com.ak.rnd.springevents.util.Utils.sleep;

@Component
@Slf4j
public class MedicalRecordsHandler implements IProcess {

    @Override
    @EventListener
    @Async
    public void execute(PatientDischargeEvent event) {
        log.info("Executing MedicalRecordsHandler for patient: {}", event.getPerson().getFirstName());
        sleep();
        log.info("Done MedicalRecordsHandler for patient: {}", event.getPerson().getFirstName());
    }
}
