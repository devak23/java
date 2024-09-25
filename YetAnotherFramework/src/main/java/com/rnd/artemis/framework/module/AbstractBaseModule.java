package com.rnd.artemis.framework.module;

import com.rnd.artemis.framework.model.DataRow;
import com.rnd.artemis.framework.model.ModuleConfig;
import com.rnd.artemis.framework.actions.ModuleAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

@Slf4j
public abstract class AbstractBaseModule implements ModuleAction {
    protected ModuleConfig moduleConfig;
    protected ApplicationContext applicationContext;

    @Override
    public void run(DataRow dataRow) {
        if (moduleConfig != null && moduleConfig.isEnabled()) {
            runModule(dataRow);
        } else {
            log.info("Either Module config is null or module is not enabled. Skipping.");
        }
    }

    @Override
    public void setConfig(ModuleConfig moduleConfig) {
        this.moduleConfig = moduleConfig;
    }
    public abstract void runModule(DataRow dataRow);
}
