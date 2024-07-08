package com.rnd.sage.framework.processors.module;

import com.rnd.sage.framework.config.ProcessorConfig;
import com.rnd.sage.framework.model.vo.ModuleVO;
import com.rnd.sage.framework.service.IAction;
import com.rnd.sage.framework.service.ICommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@Qualifier("ingestorModule")
@RequiredArgsConstructor
public class IngestorModule implements ICommand {
    private final ProcessorConfig props;
    private final ApplicationContext context;

    @Override
    public void run() {
        Optional<ModuleVO> moduleVO = props.findModule("ingestorModule");
        moduleVO.orElseThrow().getSteps().forEach( s -> {
            IAction action = (IAction) context.getBean(s.getQualifier());
            action.perform();
        });
    }
}
