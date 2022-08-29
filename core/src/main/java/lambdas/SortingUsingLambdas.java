package lambdas;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortingUsingLambdas {

    public static void main(String[] args) {
        List<Apple> apples = getApples();
//        apples.sort(new Comparator<Apple>() {
//            @Override
//            public int compare(Apple a1, Apple a2) {
//                return a1.getWeight().compareTo(a2.getWeight());
//            }
//        });
        //apples.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
        apples.sort(Comparator.comparing(Apple::getWeight));
        apples.sort(Comparator
                .comparing(Apple::getWeight)
                .reversed()
                .thenComparing(Apple::getColor)
        );
        System.out.println(apples);
    }

    private static List<Apple> getApples() {
        return Arrays.asList(
                new Apple(150.2f, COLOR.RED, COUNTRY.INDIA)
                , new Apple(144.5f, COLOR.RED, COUNTRY.UNITED_STATES)
                , new Apple(140f, COLOR.GREEN, COUNTRY.MEXICO)
                , new Apple(140f, COLOR.RED, COUNTRY.UNITED_STATES)
                , new Apple(159.3f, COLOR.GREEN, COUNTRY.UNITED_STATES)
        );
    }
}
