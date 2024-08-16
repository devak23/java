package statemachine.model;

// Fancy name for a "customer". In case of a payment, there are 2 parties involved, the from and the to. The partyType
// here signifies whether this TransactionParty is a sender or a receiver.
public record TransactionParty(String name, String bankName, PartyType partyType) {
}
