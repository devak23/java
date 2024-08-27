package com.ak.rnd.springevents.service;

import com.ak.rnd.springevents.event.PatientDischargeEvent;

public interface IProcess {
    void execute(PatientDischargeEvent event);
}
