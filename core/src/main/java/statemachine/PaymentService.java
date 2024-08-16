package statemachine;

import lombok.extern.slf4j.Slf4j;
import statemachine.model.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
public class PaymentService {
    public PaymentResult makePayment(String from, String to, BigDecimal ten) {
        Payment payment = createPayment(from, to, BigDecimal.TEN);
        return processPayment(payment);
    }

    private Payment createPayment(String from, String to, BigDecimal amount) {
        Optional<String> senderBank = getBank(from);
        TransactionParty partyFrom = new TransactionParty(from
                , senderBank.orElseThrow(() -> new RuntimeException("Sender is not registered with the bank"))
                , PartyType.SENDER);

        Optional<String> receiversBank = getBank(to);

        TransactionParty partyTo = new TransactionParty(to
                , receiversBank.orElseThrow(() -> new RuntimeException("Receiver is not registered with the bank"))
                , PartyType.RECEIVER);

        return new Payment(partyFrom, partyTo, amount, PaymentStatus.DRAFT);
    }

    private PaymentResult processPayment(Payment payment) {
        if (payment.status() == PaymentStatus.COMPLETED) {
            return createPaymentResult(payment);
        }

        PaymentResult result = createPaymentResult(payment);
        while (payment.status() != PaymentStatus.COMPLETED) {
            log.info("{}", result );

            try {
                log.info("some time has elapsed...");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Payment newPayment = movePayment(payment);
            result = createPaymentResult(newPayment);
            payment = newPayment;
        }

        return result;
    }

    private static PaymentResult createPaymentResult(Payment payment) {
        return new PaymentResult(payment.status().name(), payment.status().toString());
    }

    private Payment movePayment(Payment payment) {
        return new Payment(payment.sender(), payment.receiver(), payment.amount(), payment.status().nextState());
    }

    private Optional<String> getBank(String customer) {
        return Optional.of("HDFC Bank");
    }
}
