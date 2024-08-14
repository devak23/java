package javacodingproblems.vectors;

import jdk.incubator.vector.VectorSpecies;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VectorMain {
    static final VectorSpecies<Double> VS = VectorSpecies.ofPreferred(double.class);

    public static void main(String[] args) {
        log.info(VS.toString());
    }
}
