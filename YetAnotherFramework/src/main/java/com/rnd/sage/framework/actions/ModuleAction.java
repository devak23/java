package com.rnd.sage.framework.actions;

import com.rnd.sage.framework.model.DataRow;
import com.rnd.sage.framework.model.ModuleConfig;

public interface ModuleAction {
    void run(DataRow dataRow);
    void setConfig(ModuleConfig moduleConfig);
}
