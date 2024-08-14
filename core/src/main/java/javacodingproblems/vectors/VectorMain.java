package javacodingproblems.vectors;

import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.Vector;
import jdk.incubator.vector.VectorMask;
import jdk.incubator.vector.VectorSpecies;
import lombok.extern.slf4j.Slf4j;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.ByteOrder;

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
        int[] varr2 = new int[]{23, 21, 19, 17, 15, 13, 11, 9};
        Vector<Integer> v2 = VS256.fromArray(varr2, 0);
        log.info(v2.toString());

        // the same won't be true here...
        int[] varr3 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Vector<Integer> v3 = VS256.fromArray(varr3, 0);
        log.info(v3.toString()); // This will list elements starting from 0 to 7

        // or here...
        Vector<Integer> v4 = VS256.fromArray(varr3, 3);
        log.info(v4.toString()); // This will list elements starting from 3 to 10

        // and this will produce an IndexOutOfBoundsException since the array length is smaller than Vector's expectation
        // of 8 lanes long.
        int[] varr5 = new int[]{0, 1, 2, 3, 4};
        try {
            Vector<Integer> v5 = VS256.fromArray(varr5, 0);
            log.info(v5.toString());
        } catch (Exception e) {
            log.error("Error while creating a vector:" + e);
        }

        // Another way of creating a specialized vector is directly from IntVector like so...
        var varr6 = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
        Vector<Integer> v6 = IntVector.fromArray(VS256, varr6, 0);
        log.info(v6.toString());

        boolean[] bm = new boolean[]{false, false, true, false, false, true, true, false};
        VectorMask<Integer> m = VectorMask.fromArray(VS256, bm, 0);
        IntVector v = IntVector.fromArray(VS256, varr6, 0, m);
        log.info(v.toString());

        // another flavor of "fromArray". This time we use map of indexes to filter the selected scalars
        int[] varr7 = new int[]{11, 12, 15, 17, 20, 22, 29};
        int[] imap = new int[]{0, 0, 0, 1, 1, 6, 6, 6}; // indices that will point to the original array to pick up the numbers from
        IntVector v7 = IntVector.fromArray(VS256, varr7, 0, imap, 0);
        log.info(v7.toString()); // [11, 11, 11, 12, 12, 29, 29, 29]

        // in addition, we can use the VectorMask as well
        boolean[] bm2 = new boolean[]{false, false, true, false, false, true, true, false};
        VectorMask<Integer> mask = VectorMask.fromArray(VS256, bm2, 0);
        IntVector v8 = IntVector.fromArray(VS256, varr7, 0, imap, 0, m);
        log.info(v8.toString()); // [0, 0, 11, 0, 0, 29, 29, 0]

        // Creating a vector from memory segments
        IntVector v9;
        MemorySegment segment;
        try (Arena arena = Arena.ofConfined()) {
            segment = arena.allocate(32);
            segment.setAtIndex(ValueLayout.JAVA_INT, 0 , 11);
            segment.setAtIndex(ValueLayout.JAVA_INT, 1 , 21);
            segment.setAtIndex(ValueLayout.JAVA_INT, 2 , 12);
            segment.setAtIndex(ValueLayout.JAVA_INT, 3 , 7);
            segment.setAtIndex(ValueLayout.JAVA_INT, 4 , 33);
            segment.setAtIndex(ValueLayout.JAVA_INT, 5 , 1);
            segment.setAtIndex(ValueLayout.JAVA_INT, 6 , 3);
            segment.setAtIndex(ValueLayout.JAVA_INT, 7 , 6);
            v9 = IntVector.fromMemorySegment(VS256, segment, 0, ByteOrder.nativeOrder());
            log.info(v9.toString()); // creates: [11, 21, 12, 7, 33, 1, 3, 6]
        }

    }
}
