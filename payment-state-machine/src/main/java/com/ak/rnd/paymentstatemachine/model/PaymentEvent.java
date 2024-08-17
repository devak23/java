package com.ak.rnd.paymentstatemachine.model;

// Step 1: Define all events
public enum PaymentEvent {
    PRE_AUTHORIZE,
    PRE_AUTH_APPROVED,
    PRE_AUTH_DECLINED,
    AUTHORIZE,
    AUTH_APPROVED,
    AUTH_DECLINED
}
