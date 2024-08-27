package com.ak.rnd.springevents.handler;

import com.ak.rnd.springevents.event.PatientDischargeEvent;
import com.ak.rnd.springevents.service.IProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com.ak.rnd.springevents.util.Utils.sleep;

@Component
@Slf4j
public class NotificationHandler implements IProcess {
    @Override
    @EventListener
    public void execute(PatientDischargeEvent event) {
        log.info("Received information for patient: {}", event.getPerson().getFirstName());
        sleep();
        log.info("Done notifying information for patient: {}", event.getPerson().getFirstName());
    }
}
