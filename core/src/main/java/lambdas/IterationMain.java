package lambdas;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class IterationMain {
    private final List<String> friends = Arrays.asList("Guru", "Tejas", "Abhay", "Ashwani", "Srini", "Avinash", "Rakshpal");

    public static void main(String[] args) {
        IterationMain main = new IterationMain();
        main.iterateFriendsUsingForEach();
        main.iterateFriendsUsingLambda();
        main.iterateFriendsUsingMethodReferences();
        main.capitalize();
        main.countChars();
        main.iterateWithPredicate();
        main.findTotalChars();
        main.findTotalUniqueChars();
    }

    private void findTotalUniqueChars() {
        System.out.println("------------------UniqueChars----------------");
        Function<String, Set<Character>> splitIntoChars = (s) -> s.chars().mapToObj(c -> (char)c).collect(Collectors.toSet());
        friends.stream().map(splitIntoChars::apply);
    }

    private void findTotalChars() {
        System.out.println("---------------TotalChars-------------------");
        System.out.println("Total chars in friends: " + friends.stream().mapToInt(String::length).sum());
    }

    private void iterateWithPredicate() {
        System.out.println("---------------Predicate-------------------");
        Predicate<String> startsWithA = (s) -> s.startsWith("A");
        List<String> friendsStartingWithA = friends.stream().filter(startsWithA).collect(Collectors.toList());
        System.out.println(friendsStartingWithA);
    }

    private void countChars() {
        System.out.println("---------------Count-------------------");
        List<Integer> ints = friends.stream().map(f -> f.length()).collect(Collectors.toList());
        System.out.println(ints);
    }

    public void iterateFriendsUsingForEach() {
        System.out.println("---------------forEach-------------------");
        friends.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
    }

    public void iterateFriendsUsingLambda() {
        System.out.println("---------------Lambda-------------------");
        friends.forEach((String friend) -> System.out.println(friend));
    }

    public void iterateFriendsUsingMethodReferences() {
        System.out.println("---------------MethodRef-------------------");
        friends.forEach(System.out::println);
    }

    public void capitalize() {
        System.out.println("---------------capitalize-------------------");
        List<String> allCaps = friends.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(allCaps);
    }
}
