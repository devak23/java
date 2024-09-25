package com.rnd.artemis.framework.actions;

import com.rnd.artemis.framework.model.DataRow;
import com.rnd.artemis.framework.model.StepConfig;

public interface StepAction {
    void perform(DataRow dataRow);
    void setConfig(StepConfig config);
}
