package com.rnd.artemis.framework.actions;

import com.rnd.artemis.framework.model.DataRow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Qualifier("dataSinkAction")
public class DataSinkStepAction extends AbstractBaseStep {
    @Override
    public void performAction(DataRow row) {
        log.info("Persisting data into the database");
    }
}
