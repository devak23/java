package com.rnd.artemis.framework.actions;

import com.rnd.artemis.framework.model.DataRow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Qualifier ("dataEnrichAction")
public class DataEnrichStepActionAction extends AbstractBaseStepAction {
    @Override
    public void performAction(DataRow row) {
        log.info("Enriching data for row: {}", row);
    }
}
