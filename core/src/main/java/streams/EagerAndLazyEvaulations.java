package streams;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EagerAndLazyEvaulations {

    public static void main(String[] args) {
        EagerAndLazyEvaulations instance = new EagerAndLazyEvaulations();
        instance.demonstrateEagerEvaulation();
        System.out.println("-------------------------------------");
        instance.demonstrateLazyEvaulation();

    }

    public int square(int val) {
        return val * val;
    }

    private void demonstrateEagerEvaulation() {
        List<Integer> squares = IntStream.range(1, 11).mapToObj(this::square).toList();
        String result = squares.stream().map(String::valueOf).collect(Collectors.joining(","));
        System.out.println(result);
    }

    private void demonstrateLazyEvaulation() {
        Iterator<Integer> squares = IntStream.range(1, 11).mapToObj(this::square).iterator();
        if (squares.hasNext()) {
            System.out.println(squares.next());
            System.out.println(squares.next());
            System.out.println(squares.next());
        }
        StringBuilder builder = new StringBuilder();
        squares.forEachRemaining(s -> {
            if (!builder.isEmpty()) builder.append(",");
            builder.append(s);
        });

        String result = builder.toString();
        System.out.println(result);
    }
}
