package general;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Playground {
    interface Printer {
        void print(Object o);
    }

    public static void main(String[] args) {
        Printer p = System.out::println;
        p.print("Hello World");

        List<String> firstNames = List.of("anatoly", "robert", "borris");
        List<String> lastNames = List.of("karpov", "fischer", "spassky");
        List<String> middleInitials = List.of("y", "j", "v");
        Function<String, String> caps = (String s) -> s.substring(0, 1).toUpperCase() + s.substring(1);
        Function<String, Integer> len = String::length;

        List<String> fullNames = IntStream.range(0, firstNames.size())
                .mapToObj(i -> combineNames(i, caps, firstNames, middleInitials, lastNames))
                .toList();

        p.print(fullNames);

        List<String> snookerPlayers = List.of("Ronnie Sullivan", "Stephen Hendry", "Steve Davis", "John Higgins");
        Map<String, Integer> namesMap = snookerPlayers.stream().collect(Collectors.toMap(Function.identity(), len));
        p.print(namesMap);
    }

    private static String combineNames(int i, Function<String, String> caps, List<String> firstNames, List<String> middleInitials, List<String> lastNames) {
        return STR."\{caps.apply(firstNames.get(i))} \{middleInitials.get(i).toUpperCase()} \{caps.apply(lastNames.get(i))}";
    }
}
