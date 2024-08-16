package statemachine.model;

// What result are we expecting after payment is processed? - A status and user-friendly message
public record PaymentResult(String status, String message) {
}
