package com.rnd.sage.framework.processors.module.actions;

import com.rnd.sage.framework.service.IAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Qualifier("dataSinkAction")
public class DataSinkAction implements IAction {
    @Override
    public void perform() {
        log.info("Persisting data into the database");
    }
}
