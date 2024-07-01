package com.rnd.app.dpstrategy.strategy;

import com.rnd.app.dpstrategy.model.OutputType;
import org.springframework.stereotype.Component;

@Component
public class PDFDocCreationStrategy implements IDocCreationStrategy {
    @Override
    public String createDocument() {
        return "PDF document is created";
    }

    @Override
    public OutputType getOutputType() {
        return OutputType.PDF;
    }
}
