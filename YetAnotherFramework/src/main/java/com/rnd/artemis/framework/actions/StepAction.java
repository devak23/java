package com.rnd.artemis.framework.actions;

import com.rnd.artemis.framework.model.DataRow;
import com.rnd.artemis.framework.model.ActionConfig;

public interface StepAction {
    void perform(DataRow dataRow);
    void setConfig(ActionConfig config);
}
