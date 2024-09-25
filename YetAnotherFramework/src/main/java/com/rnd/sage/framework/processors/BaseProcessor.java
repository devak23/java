package com.rnd.sage.framework.processors;

import com.rnd.sage.framework.config.CustomYamlParserFactory;
import com.rnd.sage.framework.model.Data;
import com.rnd.sage.framework.model.ProcessorConfig;
import com.rnd.sage.framework.module.AbstractBaseModule;
import com.rnd.sage.framework.actions.ProcessAction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Qualifier("baseProcessor")
@Scope (value = "prototype")
@PropertySource(value = "classpath:/process-${artifactType}-config.yml", factory = CustomYamlParserFactory.class)
public class BaseProcessor implements ProcessAction {
    private ApplicationContext applicationContext;
    private ProcessorConfig processorConfig;

    @Override
    public void execute(Data data) {
        if (processorConfig.isEnabled()) {
            data.getData().values().parallelStream().forEach(dataRow -> {
                processorConfig.getModules().forEach(module -> {
                    AbstractBaseModule moduleAction = (AbstractBaseModule) applicationContext.getBean(module.getQualifier());
                    moduleAction.setConfig(module);
                    moduleAction.run(dataRow);
                });
            });
        }
    }
}
