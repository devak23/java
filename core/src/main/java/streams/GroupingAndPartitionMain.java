package streams;

import functional.model.Melon;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

import static functional.model.Melon.getMelons;

@Slf4j
public class GroupingAndPartitionMain {

    public static void main(String[] args) {
        List<Melon> melons = getMelons();

        partitions(melons);
        groupings(melons);
    }

    private static void groupings(List<Melon> melons) {
        Map<String, List<Melon>> byTypeInList = melons.stream().collect(Collectors.groupingBy(Melon::getType));
        log.info("Grouping by Type: {}", byTypeInList);

        Map<Float, List<Melon>> byWeightInList = melons.stream().collect(Collectors.groupingBy(Melon::getWeight));
        log.info("Grouping by Weight: {}", byWeightInList);

        Map<String, Set<Melon>> byTypeInSet = melons.stream().collect(Collectors.groupingBy(Melon::getType, Collectors.toSet()));
        log.info("Grouping by Type (no dupes): {}", byTypeInSet);

        Map<Float, Set<Melon>> byWeightInSet = melons.stream().collect(Collectors.groupingBy(Melon::getWeight, Collectors.toSet()));
        log.info("Grouping by Weight (no dupes): {}", byWeightInSet);

        Map<Float, Set<Melon>> byWeightInSetOrdered = melons.stream().collect(Collectors.groupingBy(Melon::getWeight, TreeMap::new, Collectors.toSet()));
        log.info("Grouping by Weight (no dupes and asc ordered): {}", byWeightInSetOrdered);

        Map<Float, Set<String>> byWeightInSetOrdered2 = melons.stream().collect(Collectors.groupingBy(Melon::getWeight, TreeMap::new, Collectors.mapping(Melon::getType, Collectors.toSet())));
        log.info("Grouping by Weight (no dupes and asc ordered 2): {}", byWeightInSetOrdered2);
    }

    private static void partitions(List<Melon> melons) {
        Map<Boolean, List<Melon>> byWeightWithDupes = melons.stream().collect(Collectors.partitioningBy(m -> m.getWeight() > 2000));
        log.info("Partition by weight: {}", byWeightWithDupes);

        Map<Boolean, Set<Melon>> byWeightNoDupes = melons.stream().collect(Collectors.partitioningBy(m -> m.getWeight() > 2000, Collectors.toSet()));
        log.info("Partition by weight (no dupes): {}", byWeightNoDupes);

        Map<Boolean, Long> byWeightAndCountWithDupes = melons.stream().collect(Collectors.partitioningBy(m -> m.getWeight() > 2000, Collectors.counting()));
        log.info("Partition by weight and count: {}", byWeightAndCountWithDupes);

        Map<Boolean, Long> byWeightAndCountNoDupes = melons.stream().distinct().collect(Collectors.partitioningBy(m -> m.getWeight() > 2000, Collectors.counting()));
        log.info("Partition by weight and count (no dupes): {}", byWeightAndCountNoDupes);

        Map<Boolean, Melon> byMaxWeight = melons.stream().collect(Collectors.partitioningBy(m -> m.getWeight() > 2000,
                Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingDouble(Melon::getWeight)), Optional::get)));
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