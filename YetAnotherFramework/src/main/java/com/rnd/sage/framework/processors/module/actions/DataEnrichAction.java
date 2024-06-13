package com.rnd.sage.framework.processors.module.actions;

import com.rnd.sage.framework.service.IAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Qualifier ("dataEnrichAction")
public class DataEnrichAction implements IAction {
    @Override
    public void perform() {
        log.info("Enriching the received data");
    }
}
