package com.ak.functional;

public class OperationsMain {
    public static void main(String[] args) {
        Operation<Integer, Integer> tripple = new Tripple();
        Operation<Integer, Integer> square = new Square();
        System.out.println("tripple.apply(3): " + tripple.apply(3));
        System.out.println("square.apply(4): " + square.apply(4));
        System.out.println("square.apply(tripple.apply(2)): " + square.apply(tripple.apply(2)));
        Operation<Integer, Integer> squareFirstTrippleNext = compose(tripple, square);
        Operation<Integer, Integer> trippleFirstSquareNext = compose(square, tripple);

        System.out.println("trippleFirstSquareNext(2): " + trippleFirstSquareNext.apply(2));
        System.out.println("squareFirstTrippleNext(2): " + squareFirstTrippleNext.apply(2));
    }

    public static Operation<Integer, Integer> compose(Operation<Integer, Integer> f, Operation<Integer, Integer> g) {
        return arg -> f.apply(g.apply(arg));
    }
}
