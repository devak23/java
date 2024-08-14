package javacodingproblems.vectors;

import jdk.incubator.vector.IntVector;

public class VectorOperations {
    public static void main(String[] args) {
        int[] x = {1, 2, 3, 4, 5, 6, 7, 8};
        int[] y = {8, 7, 6, 5, 4, 3, 2, 1};
        // To compute z = x + y via vector api, we have to create 2 Vector instances and rely on add() operation
        IntVector xVector = IntVector.fromArray(IntVector.SPECIES_256, x, 0);
        IntVector yVector = IntVector.fromArray(IntVector.SPECIES_256, y, 0);

        IntVector zVector = xVector.add(yVector);
        System.out.println(zVector);
    }
}
