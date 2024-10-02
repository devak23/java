package functional.patterns;

import functional.model.Customer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BuilderPatternMain {

    public static void main(String[] args) {
        Customer abhay = Customer.build(c -> c.setName("Abhay")
                        .setAddress("Fremont, CA")
                        .setEmail("abhay@gmail.com")
                        .setPhone("312-6543965")
        );

        log.info("Customer: {}", abhay);
    }
}
