package com.rnd.artemis.framework.actions;

import com.rnd.artemis.framework.model.DataRow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("dataValidateAction")
@Slf4j
public class DataValidateStepActionAction extends AbstractBaseStepAction {

    @Override
    public void performAction(DataRow row) {
        log.info("validating the data");
    }
}
