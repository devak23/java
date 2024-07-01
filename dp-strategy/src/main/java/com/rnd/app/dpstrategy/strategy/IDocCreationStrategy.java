package com.rnd.app.dpstrategy.strategy;

import com.rnd.app.dpstrategy.model.OutputType;

public interface IDocCreationStrategy {
    String createDocument();

    OutputType getOutputType();
}
