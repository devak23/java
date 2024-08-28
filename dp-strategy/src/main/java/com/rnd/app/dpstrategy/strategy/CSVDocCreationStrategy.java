package com.rnd.app.dpstrategy.strategy;

import com.rnd.app.dpstrategy.model.OutputType;
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
