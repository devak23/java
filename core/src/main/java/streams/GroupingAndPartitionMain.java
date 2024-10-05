package streams;

import functional.model.Melon;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static functional.model.Melon.get100Melons;
import static functional.model.Melon.getMelons;
import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.*;

@Slf4j
public class GroupingAndPartitionMain {

    public static void main(String[] args) {
        List<Melon> melons = getMelons();

        partitions(melons);
        groupings(melons);
        splitsByWeight();
    }

    private static void splitsByWeight() {
        List<Melon> _100Melons = get100Melons(); // assume that we have 100 Melons
        //log.info("100 melons: {}", _100Melons);

        // we want to split into 10 lists of 10 weights each. So first we find all the weights like so:
        List<Float> allWeights = _100Melons.stream().map(Melon::getWeight).toList();

        final AtomicInteger count = new AtomicInteger(0);
        Collection<List<Float>> chunkWeights = allWeights.stream()
                .collect(groupingBy(c -> count.getAndIncrement() / 10)).values();
        log.info("Chunk weights: {}", chunkWeights);
    }

    private static void groupings(List<Melon> melons) {
        Map<String, List<Melon>> byTypeInList = melons.stream().collect(groupingBy(Melon::getType));
        log.info("Grouping by Type: {}", byTypeInList);

        Map<Float, List<Melon>> byWeightInList = melons.stream().collect(groupingBy(Melon::getWeight));
        log.info("Grouping by Weight: {}", byWeightInList);

        Map<String, Set<Melon>> byTypeInSet = melons.stream().collect(groupingBy(Melon::getType, toSet()));
        log.info("Grouping by Type (no dupes): {}", byTypeInSet);

        Map<Float, Set<Melon>> byWeightInSet = melons.stream().collect(groupingBy(Melon::getWeight, toSet()));
        log.info("Grouping by Weight (no dupes): {}", byWeightInSet);

        Map<Float, Set<Melon>> byWeightInSetOrdered = melons.stream()
                .collect(groupingBy(Melon::getWeight
                        , TreeMap::new, toSet()));
        log.info("Grouping by Weight (no dupes and asc ordered): {}", byWeightInSetOrdered);

        // By default, Stream<Melon> is divided into a suite of List<Melon>. But what can we do to divide Stream<Melon>
        // into a suite of List<String>, where each list is holding only the types of melons, not the Melon instances?
        Map<Float, Set<String>> byWeightInSetOrdered2 = melons.stream()
                .collect(groupingBy(Melon::getWeight
                        , TreeMap::new
                        , mapping(Melon::getType, toSet())));
        log.info("Grouping by Weight (no dupes and asc ordered 2): {}", byWeightInSetOrdered2);

        // How can we group the lightest melons by type?
        Map<String, Optional<Melon>> minMelonsByType = melons.stream()
                .collect(groupingBy(Melon::getType
                        , minBy(comparingDouble(Melon::getWeight))));
        log.info("Grouping by Type with min weight: {}", minMelonsByType);

        // How can we group the heaviest melons by type?
        Map<String, Optional<Melon>> byTypeMaxWeight = melons.stream()
                .collect(groupingBy(Melon::getType
                        , maxBy(comparingDouble(Melon::getWeight))));
        log.info("Grouping by Type with max weight: {}", byTypeMaxWeight);

        // the issue with the above two minBy() and maxBy() implementations is that they return Optional which has to be
        // removed. If not removed the Optional will continue to adapt itself to the returned result of the collector
        // giving us different types. For these kinds of task, we have the 'collectingAndThen' factory method. This
        // method takes in a function that will be applied to the final result of the downstream collector
        Map<String, Float> minMelonByType = melons.stream()
                .collect(groupingBy(Melon::getType
                        , collectingAndThen(
                                minBy(comparingDouble(Melon::getWeight)), m -> m.orElseThrow().getWeight())));
        log.info("Grouping by type with min Weight: {}", minMelonByType);

        Map<String, Float> maxMelonByType = melons.stream()
                .collect(groupingBy(Melon::getType
                        , collectingAndThen(
                                maxBy(comparingDouble(Melon::getWeight)), m -> m.orElseThrow().getWeight())));
        log.info("Grouping by type with max Weight: {}", maxMelonByType);
    }

    private static void partitions(List<Melon> melons) {
        Map<Boolean, List<Melon>> byWeightWithDupes = melons.stream().collect(partitioningBy(m -> m.getWeight() > 2000));
        log.info("Partition by weight: {}", byWeightWithDupes);

        Map<Boolean, Set<Melon>> byWeightNoDupes = melons.stream()
                .collect(partitioningBy(m -> m.getWeight() > 2000, toSet()));
        log.info("Partition by weight (no dupes): {}", byWeightNoDupes);

        Map<Boolean, Long> byWeightAndCountWithDupes = melons.stream()
                .collect(partitioningBy(m -> m.getWeight() > 2000, counting()));
        log.info("Partition by weight and count: {}", byWeightAndCountWithDupes);

        Map<Boolean, Long> byWeightAndCountNoDupes = melons.stream()
                .distinct().collect(partitioningBy(m -> m.getWeight() > 2000, counting()));
        log.info("Partition by weight and count (no dupes): {}", byWeightAndCountNoDupes);

        Map<Boolean, Melon> byMaxWeight = melons.stream()
                .collect(partitioningBy(m -> m.getWeight() > 2000
                        , collectingAndThen(maxBy(comparingDouble(Melon::getWeight))
                                , Optional::get)));
        log.info("Partition by max weight: {}", byMaxWeight);
    }
}

// Output:
// 01:58:44.031 [main] INFO streams.GroupingAndPartitionMain -- Partition by weight: {false=[(Crenshaw:1200.0), (Hemi:1600.0), (Gac:1200.0), (Horned:1700.0)], true=[(Gac:3000.0), (Hemi:2600.0), (Apollo:2600.0), (Gac:3000.0), (Hemi:2600.0)]}
// 01:58:44.044 [main] INFO streams.GroupingAndPartitionMain -- Partition by weight (no dupes): {false=[(Crenshaw:1200.0), (Hemi:1600.0), (Gac:1200.0), (Horned:1700.0)], true=[(Apollo:2600.0), (Gac:3000.0), (Hemi:2600.0)]}
// 01:58:44.047 [main] INFO streams.GroupingAndPartitionMain -- Partition by weight and count: {false=4, true=5}
// 01:58:44.048 [main] INFO streams.GroupingAndPartitionMain -- Partition by weight and count (no dupes): {false=4, true=3}
// 01:58:44.052 [main] INFO streams.GroupingAndPartitionMain -- Partition by max weight: {false=(Horned:1700.0), true=(Gac:3000.0)}
// 01:58:44.054 [main] INFO streams.GroupingAndPartitionMain -- Grouping by Type: {Crenshaw=[(Crenshaw:1200.0)], Apollo=[(Apollo:2600.0)], Gac=[(Gac:3000.0), (Gac:1200.0), (Gac:3000.0)], Hemi=[(Hemi:2600.0), (Hemi:1600.0), (Hemi:2600.0)], Horned=[(Horned:1700.0)]}
// 01:58:44.056 [main] INFO streams.GroupingAndPartitionMain -- Grouping by Weight: {2600.0=[(Hemi:2600.0), (Apollo:2600.0), (Hemi:2600.0)], 1700.0=[(Horned:1700.0)], 1200.0=[(Crenshaw:1200.0), (Gac:1200.0)], 1600.0=[(Hemi:1600.0)], 3000.0=[(Gac:3000.0), (Gac:3000.0)]}
// 01:58:44.057 [main] INFO streams.GroupingAndPartitionMain -- Grouping by Type (no dupes): {Crenshaw=[(Crenshaw:1200.0)], Apollo=[(Apollo:2600.0)], Gac=[(Gac:3000.0), (Gac:1200.0)], Hemi=[(Hemi:2600.0), (Hemi:1600.0)], Horned=[(Horned:1700.0)]}
// 01:58:44.058 [main] INFO streams.GroupingAndPartitionMain -- Grouping by Weight (no dupes): {2600.0=[(Apollo:2600.0), (Hemi:2600.0)], 1700.0=[(Horned:1700.0)], 1200.0=[(Crenshaw:1200.0), (Gac:1200.0)], 1600.0=[(Hemi:1600.0)], 3000.0=[(Gac:3000.0)]}
// 01:58:44.059 [main] INFO streams.GroupingAndPartitionMain -- Grouping by Weight (no dupes and asc ordered): {1200.0=[(Crenshaw:1200.0), (Gac:1200.0)], 1600.0=[(Hemi:1600.0)], 1700.0=[(Horned:1700.0)], 2600.0=[(Apollo:2600.0), (Hemi:2600.0)], 3000.0=[(Gac:3000.0)]}
// 01:58:44.061 [main] INFO streams.GroupingAndPartitionMain -- Grouping by Weight (no dupes and asc ordered 2): {1200.0=[Crenshaw, Gac], 1600.0=[Hemi], 1700.0=[Horned], 2600.0=[Apollo, Hemi], 3000.0=[Gac]}