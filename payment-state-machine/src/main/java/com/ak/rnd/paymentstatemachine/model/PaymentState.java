package com.ak.rnd.paymentstatemachine.model;

// Step 2: Define all states
public enum PaymentState {
    NEW,
    PRE_AUTH,
    PRE_AUTH_ERROR,
    AUTH,
    AUTH_ERROR
}
