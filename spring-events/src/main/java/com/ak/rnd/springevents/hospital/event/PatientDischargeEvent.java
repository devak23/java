package com.ak.rnd.springevents.hospital.event;

import com.arakelian.faker.model.Person;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class PatientDischargeEvent extends ApplicationEvent {
    private final Person person;

    public PatientDischargeEvent(Object source, Person person) {
        super(source);
        this.person = person;
    }
}
