package com.rnd.artemis.framework.actions;

import com.rnd.artemis.framework.model.DataRow;
import com.rnd.artemis.framework.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Qualifier ("dataEnrichAction")
public class DataEnrichStepActionAction extends AbstractBaseStepAction {
    @Override
    public void performAction(DataRow row) {
        log.info("Enriching data ...");
        CommonUtils.introduceRandomDelay();
        log.info("Done Enriching data for row");
    }
}
