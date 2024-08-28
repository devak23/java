package com.rnd.app.strategyaslist.strategy;

import com.rnd.app.strategyaslist.model.OutputType;
import org.springframework.stereotype.Component;

@Component
public class CSVDocCreationStrategy implements IDocCreationStrategy {
    @Override
    public String createDocument() {
        return "CSV Document is created";
    }

    @Override
    public OutputType getOutputType() {
        return OutputType.CSV;
    }
}
