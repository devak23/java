package com.rnd.artemis.framework.processors;

import com.rnd.artemis.framework.config.CustomYamlParserFactory;
import com.rnd.artemis.framework.model.Data;
import com.rnd.artemis.framework.model.DataRow;
import com.rnd.artemis.framework.model.ProcessorConfig;
import com.rnd.artemis.framework.module.AbstractBaseModule;
import com.rnd.artemis.framework.actions.ProcessAction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
@Qualifier("baseProcessor")
@Scope (value = "prototype")
@PropertySource(value = "classpath:/process-${artifactType}-config.yml", factory = CustomYamlParserFactory.class)
@RequiredArgsConstructor
public class BaseProcessor implements ProcessAction {
    public static final String BASE_PROCESSOR = "baseProcessor";

    private final ApplicationContext applicationContext;
    private final ProcessorConfig processorConfig;

    @Override
    public void execute(Data data) {
        if (processorConfig.isEnabled()) {
            try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
                List<? extends Future<?>> futures = data.getData().values().stream().map(dataRow -> {
                    Runnable runnable = () -> invoke(dataRow);
                    return executorService.submit(runnable);
                }).toList();

                // do something with futures.
            }
        }
    }

    private void invoke(DataRow dataRow) {
        processorConfig.getModules().forEach(module -> {
            AbstractBaseModule moduleAction = (AbstractBaseModule) applicationContext.getBean(module.getQualifier());
            moduleAction.setConfig(module);
            moduleAction.run(dataRow);
        });
    }
}
