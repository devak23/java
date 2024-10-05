package streams;

import functional.model.Melon;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static functional.model.Melon.get100Melons;
import static functional.model.Melon.getMelons;
import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.*;

@Slf4j
public class GroupingAndPartitionMain {

    public static void main(String[] args) {
        List<Melon> melons = getMelons();
        List<Melon> _100Melons = get100Melons();

        partitions(melons);
        groupings(melons);
        splitsByWeight(_100Melons);
    }

    private static void splitsByWeight(List<Melon> _100Melons) {

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
        Map<String, Optional<Melon>> lightestMelonsByType = melons.stream()
                .collect(groupingBy(Melon::getType
                        , minBy(comparingDouble(Melon::getWeight))));
        log.info("Grouping by Type with min weight: {}", lightestMelonsByType);

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

        // We might need the group of Melons rather than their types so this comes in handy
        Map<String, Melon[]> melonArrayByType = melons.stream()
                .collect(groupingBy(Melon::getType, collectingAndThen(Collectors.toList(), l -> l.toArray(Melon[]::new))));
        melonArrayByType.forEach((t,m) -> log.info("Type: {}, Melons: {}", t, Arrays.deepToString(m)));
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
// 08:57:56.149 [main] INFO streams.GroupingAndPartitionMain -- Partition by weight: {false=[(Crenshaw:1200.0), (Hemi:1600.0), (Gac:1200.0), (Horned:1700.0), (Horned:1950.0)], true=[(Gac:3000.0), (Hemi:2600.0), (Apollo:2600.0), (Gac:3000.0), (Hemi:2600.0)]}
// 08:57:56.153 [main] INFO streams.GroupingAndPartitionMain -- Partition by weight (no dupes): {false=[(Horned:1700.0), (Gac:1200.0), (Horned:1950.0), (Hemi:1600.0), (Crenshaw:1200.0)], true=[(Apollo:2600.0), (Hemi:2600.0), (Gac:3000.0)]}
// 08:57:56.154 [main] INFO streams.GroupingAndPartitionMain -- Partition by weight and count: {false=5, true=5}
// 08:57:56.154 [main] INFO streams.GroupingAndPartitionMain -- Partition by weight and count (no dupes): {false=5, true=3}
// 08:57:56.156 [main] INFO streams.GroupingAndPartitionMain -- Partition by max weight: {false=(Horned:1950.0), true=(Gac:3000.0)}
// 08:57:56.157 [main] INFO streams.GroupingAndPartitionMain -- Grouping by Type: {Crenshaw=[(Crenshaw:1200.0)], Apollo=[(Apollo:2600.0)], Gac=[(Gac:3000.0), (Gac:1200.0), (Gac:3000.0)], Hemi=[(Hemi:2600.0), (Hemi:1600.0), (Hemi:2600.0)], Horned=[(Horned:1700.0), (Horned:1950.0)]}
// 08:57:56.158 [main] INFO streams.GroupingAndPartitionMain -- Grouping by Weight: {2600.0=[(Hemi:2600.0), (Apollo:2600.0), (Hemi:2600.0)], 1950.0=[(Horned:1950.0)], 1700.0=[(Horned:1700.0)], 1200.0=[(Crenshaw:1200.0), (Gac:1200.0)], 1600.0=[(Hemi:1600.0)], 3000.0=[(Gac:3000.0), (Gac:3000.0)]}
// 08:57:56.158 [main] INFO streams.GroupingAndPartitionMain -- Grouping by Type (no dupes): {Crenshaw=[(Crenshaw:1200.0)], Apollo=[(Apollo:2600.0)], Gac=[(Gac:1200.0), (Gac:3000.0)], Hemi=[(Hemi:2600.0), (Hemi:1600.0)], Horned=[(Horned:1700.0), (Horned:1950.0)]}
// 08:57:56.158 [main] INFO streams.GroupingAndPartitionMain -- Grouping by Weight (no dupes): {2600.0=[(Apollo:2600.0), (Hemi:2600.0)], 1950.0=[(Horned:1950.0)], 1700.0=[(Horned:1700.0)], 1200.0=[(Gac:1200.0), (Crenshaw:1200.0)], 1600.0=[(Hemi:1600.0)], 3000.0=[(Gac:3000.0)]}
// 08:57:56.158 [main] INFO streams.GroupingAndPartitionMain -- Grouping by Weight (no dupes and asc ordered): {1200.0=[(Gac:1200.0), (Crenshaw:1200.0)], 1600.0=[(Hemi:1600.0)], 1700.0=[(Horned:1700.0)], 1950.0=[(Horned:1950.0)], 2600.0=[(Apollo:2600.0), (Hemi:2600.0)], 3000.0=[(Gac:3000.0)]}
// 08:57:56.159 [main] INFO streams.GroupingAndPartitionMain -- Grouping by Weight (no dupes and asc ordered 2): {1200.0=[Crenshaw, Gac], 1600.0=[Hemi], 1700.0=[Horned], 1950.0=[Horned], 2600.0=[Apollo, Hemi], 3000.0=[Gac]}
// 08:57:56.159 [main] INFO streams.GroupingAndPartitionMain -- Grouping by Type with min weight: {Crenshaw=Optional[(Crenshaw:1200.0)], Apollo=Optional[(Apollo:2600.0)], Gac=Optional[(Gac:1200.0)], Hemi=Optional[(Hemi:1600.0)], Horned=Optional[(Horned:1700.0)]}
// 08:57:56.160 [main] INFO streams.GroupingAndPartitionMain -- Grouping by Type with max weight: {Crenshaw=Optional[(Crenshaw:1200.0)], Apollo=Optional[(Apollo:2600.0)], Gac=Optional[(Gac:3000.0)], Hemi=Optional[(Hemi:2600.0)], Horned=Optional[(Horned:1950.0)]}
// 08:57:56.160 [main] INFO streams.GroupingAndPartitionMain -- Grouping by type with min Weight: {Crenshaw=1200.0, Apollo=2600.0, Gac=1200.0, Hemi=1600.0, Horned=1700.0}
// 08:57:56.160 [main] INFO streams.GroupingAndPartitionMain -- Grouping by type with max Weight: {Crenshaw=1200.0, Apollo=2600.0, Gac=3000.0, Hemi=2600.0, Horned=1950.0}
// 08:57:56.161 [main] INFO streams.GroupingAndPartitionMain -- Chunk weights: [[2220.0, 3516.0, 4693.0, 5915.0, 1808.0, 3095.0, 4721.0, 3458.0, 4153.0, 3527.0], [2057.0, 4720.0, 3537.0, 2235.0, 4230.0, 2103.0, 3161.0, 1004.0, 5628.0, 3345.0], [4497.0, 4199.0, 3875.0, 2866.0, 3757.0, 2743.0, 3025.0, 4785.0, 5248.0, 5990.0], [4048.0, 3407.0, 1821.0, 3076.0, 1918.0, 5229.0, 4112.0, 3150.0, 4568.0, 3386.0], [3873.0, 2088.0, 1277.0, 4451.0, 5048.0, 2131.0, 5752.0, 3433.0, 1537.0, 3655.0], [5798.0, 3358.0, 5170.0, 4777.0, 1668.0, 2383.0, 4352.0, 5062.0, 4129.0, 4883.0], [2509.0, 2806.0, 4562.0, 3225.0, 4572.0, 4937.0, 4592.0, 3686.0, 4786.0, 5557.0], [1147.0, 1900.0, 5719.0, 4157.0, 2834.0, 2396.0, 5147.0, 5050.0, 1432.0, 2844.0], [1689.0, 2342.0, 3841.0, 5745.0, 1078.0, 2908.0, 1754.0, 5086.0, 5938.0, 1161.0], [2539.0, 1074.0, 2549.0, 2157.0, 3344.0, 3254.0, 1563.0, 2386.0, 4675.0, 1756.0]]
