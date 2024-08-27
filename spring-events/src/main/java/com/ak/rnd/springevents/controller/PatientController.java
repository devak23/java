package com.ak.rnd.springevents.controller;

import com.ak.rnd.springevents.event.PatientDischargeEvent;
import com.ak.rnd.springevents.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discharge")
@Slf4j
public class PatientController {
    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public String discharge() {
        var patient = Utils.getPerson();

        publisher.publishEvent(new PatientDischargeEvent(this,patient));
        log.info("Discharge event published for patient: {}", patient.getFirstName());

        return "Completed discharge for patient: " + patient.getFirstName();
    }
}
