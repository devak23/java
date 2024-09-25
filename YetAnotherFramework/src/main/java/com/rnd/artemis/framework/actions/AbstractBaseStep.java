package com.rnd.artemis.framework.actions;

import com.rnd.artemis.framework.model.DataRow;
import com.rnd.artemis.framework.model.StepConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

@Slf4j
public abstract class AbstractBaseStep implements StepAction {
    protected StepConfig stepConfig;
    protected ApplicationContext applicationContext;

    @Override
    public void perform(DataRow dataRow) {
        if (stepConfig != null && stepConfig.isEnabled()) {
            perform(dataRow);
        } else {
            log.info("Step config is disabled or is not provided");
        }
    }

    @Override
    public void setConfig(StepConfig config) {
        this.stepConfig = config;
    }

    public abstract void performAction(DataRow row);
}
