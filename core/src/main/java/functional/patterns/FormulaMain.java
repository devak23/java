package functional.patterns;

import functional.model.Formula;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class FormulaMain {

    public static void main(String[] args) throws IOException {

        // x + y = z
        double xPlusYMinusZ = Formula.compute(sc -> sc.add().add().minus().getResult());
        log.info("x + y -z = {}", xPlusYMinusZ);


        // x - y ^ sqrt(z)
        double xMinusYSqrtZ = Formula.compute(sc -> sc.add().minus().multiplyWithSqrt().getResult());
        log.info("x - y * sqrt z = {}", xMinusYSqrtZ);
     }
}
