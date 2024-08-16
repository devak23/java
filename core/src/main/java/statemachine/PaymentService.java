package statemachine;

import lombok.extern.slf4j.Slf4j;
import statemachine.model.*;
import statemachine.util.YamlUtil;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

// The class which actually does the job of processing the payment. HEre, our focus is basically to move the payment
// in different states. This is done in the method processPayment(). However, the customer/client only "see's" the
// makePayment() method which accepts a very simplistic input viz: a Sender, a Receiver and an Amount. Rest of the
// gory details is hidden from the client. Check PaymentMain.java
@Slf4j
public class PaymentService {
    private CustomerRegisteredBanks customerRegisteredBanks
            = YamlUtil.loadYaml("customer-registered-banks.yaml", CustomerRegisteredBanks.class);

    public PaymentResult makePayment(String from, String to, BigDecimal amount) {
        Payment payment = createPayment(from, to, amount);
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
            return createResult(payment);
        }

        PaymentResult result = createResult(payment);
        while (payment.status() != PaymentStatus.COMPLETED) {
            log.info("{}", result );

            try {
                log.info("some time has elapsed...");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Payment newPayment = movePayment(payment);
            result = createResult(newPayment);
            payment = newPayment;
        }

        return result;
    }

    private static PaymentResult createResult(Payment payment) {
        return new PaymentResult(payment.status().name(), payment.status().toString());
    }

    private Payment movePayment(Payment payment) {
        return new Payment(payment.sender(), payment.receiver(), payment.amount(), payment.status().nextState());
    }

    private Optional<String> getBank(String customer) {
        return Optional.of(customerRegisteredBanks.getBankBranch().get(customer));
    }
}
