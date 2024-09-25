package com.rnd.sage.framework.actions;

import com.rnd.sage.framework.model.DataRow;
import com.rnd.sage.framework.model.StepConfig;

public interface StepAction {
    void perform(DataRow dataRow);
    void setConfig(StepConfig config);
}
