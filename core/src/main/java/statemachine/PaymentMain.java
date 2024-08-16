package statemachine;

import lombok.extern.slf4j.Slf4j;
import statemachine.model.PaymentResult;

import java.math.BigDecimal;

@Slf4j
public class PaymentMain {
    public static void main(String[] args) {
        PaymentService paymentService = new PaymentService();
        PaymentResult result = paymentService.makePayment("abhay", "guru", BigDecimal.TEN);
        log.info("result = {}", result);
    }
}
