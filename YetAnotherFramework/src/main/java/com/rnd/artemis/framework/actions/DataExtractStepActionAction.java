package com.rnd.artemis.framework.actions;

import com.rnd.artemis.framework.model.DataRow;
import com.rnd.artemis.framework.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier ("dataExtractAction")
@Slf4j
public class DataExtractStepActionAction extends AbstractBaseStepAction {
    @Override
    public void performAction(DataRow row) {
        log.info("Extracting data from message...");
        CommonUtils.introduceRandomDelay();
        log.info("Done Extracting data");
    }
}
