package com.ak.rnd.springevents.hospital.service;

import com.ak.rnd.springevents.hospital.event.PatientDischargeEvent;

public interface IProcess {
    void execute(PatientDischargeEvent event);
}
