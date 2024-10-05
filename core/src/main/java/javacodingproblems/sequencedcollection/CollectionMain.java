package javacodingproblems.sequencedcollection;

import com.arakelian.faker.model.Person;
import com.arakelian.faker.service.RandomPerson;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class CollectionMain {

    public static void main(String[] args) {

        Function<List<Person>, List<String>> extractFirstNames = people ->
                Collections.singletonList(
                        people.stream()
                                .map(Person::getFirstName)
                                .collect(Collectors.joining(","))
                );

        List<Person> people = RandomPerson.get().listOf(5);
        log.info("list of people = {}", extractFirstNames.apply(people));
        // list of people = [JONA,LYNWOOD,JAY,SIOBHAN,SHERILYN]

        printFirstAndLast(people);
        Person randomP1 = RandomPerson.get().next();
        Person randomP2 = RandomPerson.get().next();
        log.info("Adding randomP1 = {} to the first place in the list", randomP1.getFirstName());
        people.addFirst(randomP1);
        printFirstAndLast(people);
        log.info("Adding randomP2 = {} to the last place in the list", randomP2.getFirstName());
        people.addLast(randomP2);
        printFirstAndLast(people);
        log.info("Removing first Person from the list");
        people.removeFirst();
        printFirstAndLast(people);
        log.info("Removing last Person from the list");
        people.removeLast();
        printFirstAndLast(people);

        // This works as expected:
        // CollectionMain -- Now, the First Person = THADDEUS
        // CollectionMain -- Now, the Last Person = ASHLY
        // CollectionMain -- Adding randomP1 = KATIA to the first place in the list
        // CollectionMain -- Now, the First Person = KATIA
        // CollectionMain -- Now, the Last Person = ASHLY
        // CollectionMain -- Adding randomP2 = DUNCAN to the last place in the list
        // CollectionMain -- Now, the First Person = KATIA
        // CollectionMain -- Now, the Last Person = DUNCAN
        // CollectionMain -- Removing first Person from the list
        // CollectionMain -- Now, the First Person = THADDEUS
        // CollectionMain -- Now, the Last Person = DUNCAN
        // CollectionMain -- Removing last Person from the list
        // CollectionMain -- Now, the First Person = THADDEUS
        // CollectionMain -- Now, the Last Person = ASHLY

        // reversing the collection:
        List<Person> reversedPeople = people.reversed();
        log.info("reversed list of people = {}", extractFirstNames.apply(reversedPeople));
        log.info("original list of people = {}", extractFirstNames.apply(people));
    }

    private static void printFirstAndLast(List<Person> people) {
        log.info("Now, the First Person = {}", people.getFirst().getFirstName());
        log.info("Now, the Last Person = {}", people.getLast().getFirstName());
    }
}
