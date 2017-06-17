package com.ak.learning.patterns.visitor;


import java.util.HashSet;
import java.util.Set;

public class TestClient {

    public static void main(String... args) {
        TaxVisitor taxCalc = new TaxVisitor();

        /* This will not work */
//        for(Visitable visitable: visitables) {
//            System.out.println(taxCalc.visit(visitable));
//        }

        /* This wont work too. */
//        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        for(Item item: getItems()) {
//            System.out.println(taxCalc.visit(item));
//        }

        /* This works!! */
//        Necessity milk = new Necessity("milk", 3.47);
//        Liquor vodka = new Liquor("vodka", 19.99);
//        Tobacco cigars = new Tobacco("cigars", 29.67);
//
//        System.out.println("milk price after taxes: " + taxCalc.visit(milk));
//        System.out.println("vodka price after taxes: " + taxCalc.visit(vodka));
//        System.out.println("cigar price after taxes: " + taxCalc.visit(cigars));

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
        for(Visitable visitable: getVisitables()) {
            System.out.println(visitable.accept(taxCalc));
        }
    }

    public static Set<Visitable> getVisitables() {
        Necessity milk = new Necessity("milk", 3.47);
        Liquor vodka = new Liquor("vodka", 19.99);
        Tobacco cigars = new Tobacco("cigars", 29.67);

        Set<Visitable> visitables = new HashSet<>();
        visitables.add(milk);
        visitables.add(vodka);
        visitables.add(cigars);

        return visitables;
    }

    public static Set<Item> getItems() {
        Necessity milk = new Necessity("milk", 3.47);
        Liquor vodka = new Liquor("vodka", 19.99);
        Tobacco cigars = new Tobacco("cigars", 29.67);

        Set<Item> items = new HashSet<>();
        items.add(milk);
        items.add(vodka);
        items.add(cigars);

        return items;
    }
}
