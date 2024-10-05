package streams;

import functional.model.Melon;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
public class ComparingMain {

    public static void main(String[] args) {

        List<Melon> melons = Melon.getMelons();

        Comparator<Melon> byWeight = Comparator.comparingDouble(Melon::getWeight);
        Comparator<Melon> byType = Comparator.comparing(Melon::getType);
        Comparator<Melon> byWeightAndType = byWeight.thenComparing(byType);
        Comparator<Melon> byTypeAndWeight = byType.thenComparing(byWeight);

        List<Melon> sortedByWeightAndType = melons.stream().sorted(byWeightAndType).toList();
        log.info("Sorted  Melons by weight and then Type: {}", sortedByWeightAndType);

        List<Melon> sortedByTypeAndWeight = melons.stream().sorted(byTypeAndWeight).toList();
        log.info("Sorted  Melons by type and then Weight: {}", sortedByTypeAndWeight);

        Comparator<Melon> byTypeCaseInsensitiveAndWeight =
                Comparator.comparing(Melon::getType, String.CASE_INSENSITIVE_ORDER).thenComparing(Melon::getWeight);
        List<Melon> sortedByTypeCaseInsensitiveAndWeight = Melon.getCaseSensitiveMelonsType().stream().sorted(byTypeCaseInsensitiveAndWeight).toList();
        log.info("Sorted by Type(non case-insensitive) and then weight: {}", sortedByTypeCaseInsensitiveAndWeight);

        // if we want to have the comparison in a case-sensitive way, then we just drop the parameter "String.CASE_INSENSITIVE_ORDER"
        Comparator<Melon> byTypeCaseSensitiveAndWeight = Comparator.comparing(Melon::getType).thenComparing(Melon::getWeight);
        List<Melon> sortedMelonsByTypeAndWeight = Melon.getCaseSensitiveMelonsType().stream().sorted(byTypeCaseSensitiveAndWeight).toList();
        log.info("Sorted by Type(case sensitive) and then weight: {}", sortedMelonsByTypeAndWeight);

        Predicate<Melon> weightMoreThan2000 = melon -> melon.getWeight() > 2000f;
        Predicate<Melon> gacOrApollo = melon -> melon.getType().equalsIgnoreCase("gac") || melon.getType().equalsIgnoreCase("apollo");
        List<Melon> gacOrApolloMoreThan2000 = melons.stream().filter(weightMoreThan2000).filter(gacOrApollo).toList();
        log.info("Gac or Apollo more than 20000: {}", gacOrApolloMoreThan2000);

        Predicate<Melon> notMorethan2000 = Predicate.not(weightMoreThan2000);
        Predicate<Melon> restOfMelons = Predicate.not(gacOrApollo);
        List<Melon> restOfTheMelons = melons.stream().filter(notMorethan2000).filter(restOfMelons).toList();
        log.info("Other melons: {}", restOfTheMelons);
    }
}
