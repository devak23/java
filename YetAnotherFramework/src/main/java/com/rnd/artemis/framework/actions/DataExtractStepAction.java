package com.rnd.artemis.framework.actions;

import com.rnd.artemis.framework.model.DataRow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier ("dataExtractAction")
@Slf4j
public class DataExtractStepAction extends AbstractBaseStep {
    @Override
    public void performAction(DataRow row) {
        log.info("DataExtractStepAction performAction");
    }
}
