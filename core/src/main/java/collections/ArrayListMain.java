package collections;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class ArrayListMain {

    public static void main(String[] args) {
        List<String> friends = getFriends();
        String expectedOutput1 = "Dhiren,Meenakshi,Nimisha,Saif";


        IntStream.range(0, 1000).forEach(i -> {
            performTest(expectedOutput1, friends);
        });
        log.info("ArrayList MAINTAINS the order of elements while retrieval");

        log.info("=========================dynamic loading===========================================");

        String expectedOutput2 = "Suhas,Kavita,Abrar,Varkha";
        IntStream.range(0, 1000).forEach(i -> {
            performTest(expectedOutput2, null);
        });

        log.info("ArrayList MAINTAINS the order of elements while retrieval even if the elements are added dynamically");

    }

    private static void performTest(String expectedOutput, List<String> friends) {
        if (friends == null) {
            friends = new ArrayList<>(10);
            friends.add("Suhas");
            friends.add("Kavita");
            friends.add("Abrar");
            friends.add("Varkha");
        }


        String returnedString = iterateAndBuildString(friends);
        if (!returnedString.equals(expectedOutput)) {
            throw new RuntimeException("ArrayList DOES NOT maintain order during retrieval when dynamically added the elements");
        }
    }

    private static String iterateAndBuildString(List<String> friends) {
        StringBuilder builder = new StringBuilder();
        for(String friend: friends) {
            builder.append(friend).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    private static List<String> getFriends() {
        ArrayList<String> friends = new ArrayList<>();
        friends.add("Dhiren");
        friends.add("Meenakshi");
        friends.add("Nimisha");
        friends.add("Saif");
        return friends;
    }
}
