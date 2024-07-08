package com.rnd.sage.framework.config;

import com.rnd.sage.framework.model.vo.ModuleVO;
import com.rnd.sage.framework.model.vo.ProcessorVO;
import com.rnd.sage.framework.model.vo.StepVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Optional;

@Configuration
@ConfigurationProperties(prefix = "sage.artifact")
@PropertySource(value = "classpath:/process-${artifactType}-config.yml", factory = CustomYamlParserFactory.class)
@Getter
@Setter
public class ProcessorConfig {
    private List<ProcessorVO> processors;

    public Optional<ProcessorVO> findProcessor(String qualifier) {
        return processors.stream().filter(e -> e.getQualifier().equalsIgnoreCase(qualifier)).findFirst();
    }

    public Optional<ModuleVO> findModule(String qualifier) {
        List<ModuleVO> modules = getModuleVOS();
        return modules.stream().filter(m -> m.getQualifier().equalsIgnoreCase(qualifier)).findFirst();
    }

    public Optional<StepVO> findStep(String qualifier) {
        List<ModuleVO> modules = getModuleVOS();
        List<StepVO> steps = getStepVOS(modules);

        return steps.stream().filter(s -> s.getQualifier().equalsIgnoreCase(qualifier)).findFirst();
    }


    private List<ModuleVO> getModuleVOS() {
        return processors.stream()
                .map(ProcessorVO::getModules)
                .flatMap(List::stream)
                .toList();
    }

    private static List<StepVO> getStepVOS(List<ModuleVO> modules) {
        return modules.stream()
                .map(ModuleVO::getSteps)
                .flatMap(List::stream)
                .toList();
    }
}
