package com.ak.learning.patterns.nullobject;

public final class NullCustomer extends AbstractCustomer {
    public static final NullCustomer INSTANCE = new NullCustomer();

    private NullCustomer() {

    }

    public static final NullCustomer getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public String getName() {
        return "Not Available";
    }
}
