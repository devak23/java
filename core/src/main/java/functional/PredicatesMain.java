package functional;

import com.arakelian.faker.model.Gender;
import com.arakelian.faker.model.Person;
import com.arakelian.faker.service.RandomPerson;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class PredicatesMain {

    public static void main(String[] args) {
        // So what is a predicate? - In English, it means the part of the sentence which tells what the "subject" does.
        // Ex: She had ice-cream after dinner. In this line, "she" is the subject, "had" is the verb and whatever else that
        // talks about the subject is predicate i.e. "ice-cream after dinner". Predicates in Java are no different.
        // They tell about the "subject" that you're playing with.
        // Munnabhai - Abe yeh subject, subject kya bolrahela hai... ek rakh ke doon kya tereko?? :X :X :X

        // So how do we define a predicate? It's nothing more than a lambda expression. For ex: if you wanted to see if
        // the string length is even, you would write something like:
        Predicate<String> lengthIsEven = s -> s.length() % 2 == 0;
        // So we defined a predicate that checks the length of a given string and returns true if its even or odd otherwise.

        // Great! How do we use it? We will use the "test" method of the predicate to actually "use the predicate" .
        boolean result = lengthIsEven.test("Hello World!");

        System.out.println("result = " + result); // evaluates to true! (dont miss the space between the two words)

        // Now, Say you have a list of 20 People
        List<Person> people = RandomPerson.get().listOf(20); // how did I get this? courtesy: faker api..

        /*
         *         <dependency>
         *             <groupId>com.arakelian</groupId>
         *             <artifactId>faker</artifactId>
         *             <version>4.0.1</version>
         *         </dependency>
         */

        // and you want to filter out MALE's ... er... lets say Females... how do we do it?
        List<Person> females = people.stream().filter(p -> p.getGender().equals(Gender.FEMALE)).toList();
        System.out.println("females = " + females);
        // right? this is how we do it. Now, how do we do it with Predicates?


        // so first we build the predicate...
        Predicate<Person> isFemale = p -> p.getGender().equals(Gender.FEMALE);
        // and then we simply apply it:
        List<Person> femalesAgain = people.stream().filter(isFemale).toList();
        System.out.println("femalesAgain = " + femalesAgain);
        // doesn't that look sweeter than the earlier lambda function? It does! and its more readable!
        // (except the predicate function itself!)

        // So what to do if you wanted all the Females whose first name starts with S. Again - build a predicate first
        Predicate<Person> nameStartsWithS = p -> p.getFirstName().startsWith("S") || p.getFirstName().startsWith("s");
        // and then we apply it using the filter.
        List<Person> femalesWithNameStartingWithS = people.stream()
                .filter(isFemale)
                .filter(nameStartsWithS)
                .toList();
        System.out.println("femalesWithNameStartingWithS = " + femalesWithNameStartingWithS);

        // As you can see we are using the filter again and again... Can there be any better way? The great chess
        // grandmaster Emanuel Lasker once famously said - "If you find a good move, look for a better one". Bobby
        // Fischer said - "You've got to play the bad move in your head and then play the good move on the board."
        // and Kasparov said ... well I digressed...lets come back. So can there be a better way? Indeed!!!

        Predicate<Person> femaleWithNameS = isFemale.and(nameStartsWithS); // VOILA!!!!
        // How do you apply it? Elementary my dear watson!

        List<Person> femalesWithNameStartingWithSAgain = people.stream().filter(femaleWithNameS).toList();
        // hehehe... how about that huh?! better than you have been writing that shit so far... right? :)
        // Now imagine if you could define all Predicates in a file called Predicates.java and simply reference it,
        // then your code actually becomes a lot sweeter ... no?

        System.out.println("femalesWithNameStartingWithSAgain = " + femalesWithNameStartingWithSAgain);

        // So you know that you can and(), or() and negate() using Predicates. You have to be careful around and() and
        // nulls because you could technically get something which is null and if you and() with it... you get
        // NullPointerException. So how to safeguard?
        Predicate<Person> isNotNull = Objects::nonNull;
        Predicate<Person> isFemaleAgain =  p -> p.getGender().equals(Gender.FEMALE);
        Predicate<Person> nameWithS = p -> p.getFirstName().toUpperCase().startsWith("S");
        // Here the not null check should come first else everything else goes to trash!
        Predicate<Person> notNullFemaleWithNameStartingWithS = isNotNull.and(isFemaleAgain).and(nameWithS);

        // How to use it? ... Now you know!
        List<Person> notNullFemalesWithNameWithS = people.stream().filter(notNullFemaleWithNameStartingWithS).toList();
        System.out.println(notNullFemalesWithNameWithS);
    }
}
