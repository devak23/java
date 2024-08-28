package com.rnd.app.strategyaslist.strategy;

import com.rnd.app.strategyaslist.model.OutputType;

public interface IDocCreationStrategy {
    String createDocument();

    OutputType getOutputType();
}
