package statemachine.model;

import java.math.BigDecimal;

// What does a Payment consist of? A sender, a receiver, the amount and the status of the payment.
public record Payment(TransactionParty sender
        , TransactionParty receiver
        , BigDecimal amount
        , PaymentStatus status) {
}

