package javacodingproblems.vectors;

import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorMask;
import jdk.incubator.vector.VectorSpecies;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class VectorOperations {
    private static final VectorSpecies<Integer> VS256 = IntVector.SPECIES_256;

    public static void main(String[] args) {
        int[] x = {1, 2, 3, 4, 5, 6, 7, 8};
        int[] y = {8, 7, 6, 5, 4, 3, 2, 1};
        IntVector result = addVectors(x, y);
        log.info(result.toString()); // prints [9, 9, 9, 9, 9, 9, 9, 9]

        x = new int[] {3, 6, 5, 5, 1, 2, 3, 4, 5, 6, 7, 8, 3, 6, 5, 5, 1, 2, 3,
                4, 5, 6, 7, 8, 3, 6, 5, 5, 1, 2, 3, 4, 3, 4};
        y = new int[] {4, 5, 2, 5, 1, 3, 8, 7, 1, 6, 2, 3, 1, 2, 3, 4, 5, 6, 7,
                8, 3, 6, 5, 5, 1, 2, 3, 4, 5, 6, 7, 8, 2, 8};

        result = addVectors(x, y);
        log.info(result.toString()); // prints [7, 11, 7, 10, 2, 5, 11, 11] as the other elements of x and y will be
        // ignored. The reason is Vector is based on SPECIES_256. If we choose SPECIES_PREFERRED, then the result may
        // not be consistent because it will depend on the platform to choose the number of vector lanes depending on
        // what the architecture allows.

        // So we need to chunk arrays and use loop to traverse the arrays and compute the result chunk.
        var z = new int[34];

        addChunks(x, y, z);
        log.info("Final array z = {}", Arrays.toString(z));
    }

    private static void addChunks(int[] x, int[] y, int z[]) {
        // how big should a chunk be? The first challenge is represented by the loop design. The loop should start from
        // 0, but what are the upper bound and the step? Typically, the upper bound is the length of x (so 34). But,
        // using x.length is not exactly useful because it doesn’t guarantee that our vectors will accommodate as many
        // scalars as possible from the arrays. What we are looking for is the largest multiple of VLENGTH (vector’s
        // length) that is less than or equal to x.length. In our case, that is the largest multiple of 8 that is less
        // than 34, so 32. This is exactly what the loopBound() method returns
        int upperBound = VS256.loopBound(x.length);
        log.info("loopBound = {}", upperBound);

        int i = 0;
        for(; i < upperBound; i += VS256.length()) {
            IntVector xVector = IntVector.fromArray(VS256, x, i);
            IntVector yVector = IntVector.fromArray(VS256, y, i);
            IntVector zVector = xVector.add(yVector);
            zVector.intoArray(z, i); // This will produce: [7, 11, 7, 10, 2, 5, 11, 11, 6, 12, 9, 11, 4, 8, 8, 9, 6, 8,
            // 10, 12, 8, 12, 12, 13, 4, 8, 8, 9, 6, 8, 10, 12, 0, 0]
        }

        // The last 2 zeros: 0, 0 are items that have not been computed. Its the difference between x.length
        // - upperBound. For the array length that matches exactly with Vector length, this difference will be zero
        // Covering the remaining items can be accomplished in at least two ways: using VectorMask or using the
        // traditional approach

        if (i <= (x.length - 1)) {
            VectorMask<Integer> mask = VS256.indexInRange(i, x.length); // The indexInRange() computes a mask in the range [i, x.length-1]
            log.info("mask = {}", mask.toVector()); // [-1, -1, 0, 0, 0, 0, 0, 0]
            log.info("x = {}", Arrays.toString(x)); // [3, 6, 5, 5, 1, 2, 3, 4, 5, 6, 7, 8, 3, 6, 5, 5, 1, 2, 3, 4, 5, 6, 7, 8, 3, 6, 5, 5, 1, 2, 3, 4, 3, 4]
            log.info("y = {}", Arrays.toString(y)); // [4, 5, 2, 5, 1, 3, 8, 7, 1, 6, 2, 3, 1, 2, 3, 4, 5, 6, 7, 8, 3, 6, 5, 5, 1, 2, 3, 4, 5, 6, 7, 8, 2, 8]
            IntVector xVector = IntVector.fromArray(VS256, x, i, mask);
            IntVector yVector = IntVector.fromArray(VS256, y, i, mask);
            log.info("xVector = {}", xVector); // [3, 4, 0, 0, 0, 0, 0, 0]
            log.info("yVector = {}", yVector); // [2, 8, 0, 0, 0, 0, 0, 0]
            IntVector zVector = xVector.add(yVector);
            log.info("zVector = {}", zVector.toString()); // [5, 12, 0, 0, 0, 0, 0, 0]
            zVector.intoArray(z, i, mask);
            // This prints: [7, 11, 7, 10, 2, 5, 11, 11, 6, 12, 9, 11, 4, 8, 8, 9, 6, 8, 10, 12, 8, 12, 12, 13, 4, 8, 8, 9, 6, 8, 10, 12, 5, 12]
            // as expected.
        }
    }


    private static IntVector addVectors(int[] x, int[] y) {
        // To compute z = x + y via vector api, we have to create 2 Vector instances and rely on add() operation
        IntVector xVector = IntVector.fromArray(IntVector.SPECIES_256, x, 0);
        IntVector yVector = IntVector.fromArray(IntVector.SPECIES_256, y, 0);
        // In Java, an integer needs 4 bytes, so 32 bits. Since x and y hold 8 integers, we need 8*32=256 bits to
        //represent them in our vector. So, relying on SPECIES_256 is the right choice.

        return xVector.add(yVector);
    }
}
