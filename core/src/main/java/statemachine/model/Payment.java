package statemachine.model;

import java.math.BigDecimal;

public record Payment(TransactionParty sender
        , TransactionParty receiver
        , BigDecimal amount
        , PaymentStatus status) {
}

