package com.rnd.app.dpstrategy.strategy;

import com.rnd.app.dpstrategy.model.OutputType;
import org.springframework.stereotype.Component;

@Component
public class HtmlDocCreationStrategy implements IDocCreationStrategy {
    @Override
    public String createDocument() {
        return "HTML Document is created";
    }

    @Override
    public OutputType getOutputType() {
        return OutputType.HTML;
    }
}
