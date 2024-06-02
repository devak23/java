package qna;

import qna.exception.InvalidInputException;

/**
 * Problem: Rotate an array of n elements to the right by k steps. For example, with n = 7 and k = 3, the array
 * [1,2,3,4,5,6,7] is rotated to [5,6,7,1,2,3,4].
 */
public class RotateArray {
    public int[] rotate(int[] input, int steps) throws InvalidInputException {
        if (steps < 0) {
            throw new InvalidInputException("Steps cannot be negative");
        }

        if (steps == 0) {
            return input;
        }

        int[] firstPart = new int[input.length - steps];
        System.arraycopy(input, 0, firstPart, 0, input.length - steps); // will copy [1,2,3,4]

        int[] secondPart = new int[steps];
        System.arraycopy(input, input.length - firstPart.length+1, secondPart, 0, steps); // will copy [5,6,7]

        int[] finalArray = new int[firstPart.length + secondPart.length];
        System.arraycopy(secondPart, 0, finalArray, 0, secondPart.length); // will copy [5,6,7,0,0,0,0]
        System.arraycopy(firstPart, 0, finalArray, secondPart.length, firstPart.length); // will copy [5,6,7,1,2,3,4]

        return finalArray;
    }
}
