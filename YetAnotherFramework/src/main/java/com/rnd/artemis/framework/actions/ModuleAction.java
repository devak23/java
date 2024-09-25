package com.rnd.artemis.framework.actions;

import com.rnd.artemis.framework.model.DataRow;
import com.rnd.artemis.framework.model.ModuleConfig;

public interface ModuleAction {
    void run(DataRow dataRow);
    void setConfig(ModuleConfig moduleConfig);
}
