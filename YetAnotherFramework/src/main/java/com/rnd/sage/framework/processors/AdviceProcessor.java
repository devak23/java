package com.rnd.sage.framework.processors;

import com.rnd.sage.framework.config.ProcessorConfig;
import com.rnd.sage.framework.model.vo.ProcessorVO;
import com.rnd.sage.framework.service.ICommand;
import com.rnd.sage.framework.service.IProcess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Qualifier("adviceProcessor")
@Service
public class AdviceProcessor implements IProcess {
    private final ProcessorConfig props;
    private final ApplicationContext context;

    @Override
    public void execute() {
        Optional<ProcessorVO> processorVO = props.findProcessor("adviceProcessor");
        processorVO.orElseThrow().getModules().forEach(m -> {
            ICommand bean = (ICommand) context.getBean(m.getQualifier());
            bean.run();
        });

    }
}
