package com.ak.rnd.architecture.processor;

public class AdviceProcessor implements IProcess<String> {

    public AdviceProcessor() {
        // required for reflection
    }

    @Override
    public String process() {
        return "Executing AdviceProcessor";
    }
}
