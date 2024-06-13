package com.rnd.sage.framework.config;

import com.rnd.sage.framework.model.vo.ModuleVO;
import com.rnd.sage.framework.model.vo.ProcessorVO;
import com.rnd.sage.framework.model.vo.StepVO;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Optional;

@Configuration
@ConfigurationProperties(prefix = "sage.artifact")
@PropertySource(value = "classpath:/process-${artifactType}-config.yml", factory = CustomYamlParserFactory.class)
@Getter
public class ProcessorConfig {
    private List<ProcessorVO> processors;

    public Optional<ProcessorVO> findProcessor(String qualifier) {
        return processors.stream().filter(e -> e.getQualifier().equalsIgnoreCase(qualifier)).findFirst();
    }

    public Optional<ModuleVO> findModule(String qualifier) {
        List<ModuleVO> modules = processors.stream().map(p -> (ModuleVO) p.getModuleVOs()).toList();
        return modules.stream().filter(m -> m.getQualifier().equalsIgnoreCase(qualifier)).findFirst();
    }

    public Optional<StepVO> findStep(String qualifier) {
        List<ModuleVO> modules = processors.stream().map(p -> (ModuleVO) p.getModuleVOs()).toList();
        List<StepVO> steps = modules.stream().map(m -> (StepVO) m.getStepsVO()).toList();
        return steps.stream().filter(s -> s.getQualifier().equalsIgnoreCase(qualifier)).findFirst();
    }
}
