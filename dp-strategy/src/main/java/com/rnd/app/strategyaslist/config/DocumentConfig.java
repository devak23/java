package com.rnd.app.strategyaslist.config;

import com.rnd.app.strategyaslist.strategy.IDocCreationStrategy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Getter
@Setter
@RequiredArgsConstructor
public class DocumentConfig {
    private final List<IDocCreationStrategy> creationStrategies;
}
