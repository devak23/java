package com.rnd.sage.framework.processors.module.actions;

import com.rnd.sage.framework.service.IAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier ("dataExtractAction")
@Slf4j
public class DataExtractAction implements IAction {
    @Override
    public void perform() {
      log.info("Extracting data");
    }
}
