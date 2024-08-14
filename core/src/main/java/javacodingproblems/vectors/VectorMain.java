package javacodingproblems.vectors;

import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.Vector;
import jdk.incubator.vector.VectorMask;
import jdk.incubator.vector.VectorSpecies;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VectorMain {
    static final VectorSpecies<Integer> VS256 = IntVector.SPECIES_256;

    public static void main(String[] args) {
        log.info(VS256.toString());
        log.info("Element type: {}, Shape: {}", VS256.elementType(), VS256.vectorShape());

        Vector<Integer> v1 = VS256.zero(); // This produces a vector with 8 lanes of 0
        log.info(v1.toString());
        IntVector intVector = IntVector.zero(VS256); // leads to the same thing as well
        log.info(intVector.toString());

        // to load a vector with a defined primitive value...
        IntVector intVector2 = IntVector.broadcast(VS256, 5);
        log.info(intVector2.toString());

        // the most common way of creating vectors
        int[] varr2 = new int[] {23, 21, 19, 17, 15, 13, 11, 9};
        Vector<Integer> v2 = VS256.fromArray(varr2, 0);
        log.info(v2.toString());

        // the same won't be true here...
        int[] varr3 = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Vector<Integer> v3 = VS256.fromArray(varr3, 0);
        log.info(v3.toString()); // This will list elements starting from 0 to 7

        // or here...
        Vector<Integer> v4 = VS256.fromArray(varr3, 3);
        log.info(v4.toString()); // This will list elements starting from 3 to 10

        // and this will produce an IndexOutOfBoundsException since the array length is smaller than Vector's expectation
        // of 8 lanes long.
        int[] varr5 = new int[] {0, 1, 2, 3, 4};
        try {
            Vector<Integer> v5 = VS256.fromArray(varr5, 0);
            log.info(v5.toString());
        } catch (Exception e) {
            log.error("Error while creating a vector:" + e);
        }

        // Another way of creating a specialized vector is directly from IntVector like so...
        var varr6 = new int[] {0, 1, 2, 3, 4, 5, 6, 7};
        Vector<Integer> v6 = IntVector.fromArray(VS256, varr6, 0);
        log.info(v6.toString());

        boolean[] bm = new boolean[] {false, false, true, false, false, true, true, false};
        VectorMask<Integer> m = VectorMask.fromArray(VS256, bm, 0);
        IntVector v = IntVector.fromArray(VS256, varr6, 0, m);
        log.info(v.toString());
    }
}
