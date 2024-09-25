package com.rnd.artemis.framework.actions;

import com.rnd.artemis.framework.model.DataRow;
import com.rnd.artemis.framework.model.ActionConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

@Slf4j
public abstract class AbstractBaseStep implements StepAction {
    protected ActionConfig actionConfig;
    protected ApplicationContext applicationContext;

    @Override
    public void perform(DataRow dataRow) {
        if (actionConfig != null && actionConfig.isEnabled()) {
            perform(dataRow);
        } else {
            log.info("Step config is disabled or is not provided");
        }
    }

    @Override
    public void setConfig(ActionConfig config) {
        this.actionConfig = config;
    }

    public abstract void performAction(DataRow row);
}
