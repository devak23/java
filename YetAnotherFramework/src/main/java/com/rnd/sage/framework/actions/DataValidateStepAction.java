package com.rnd.sage.framework.actions;

import com.rnd.sage.framework.model.DataRow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("dataValidateAction")
@Slf4j
public class DataValidateStepAction extends AbstractBaseStep {

    @Override
    public void performAction(DataRow row) {
        log.info("validating the data");
    }
}
