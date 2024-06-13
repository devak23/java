package com.rnd.sage.framework.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ModuleVO extends BaseVO {
    private List<StepVO> stepsVO;
}
