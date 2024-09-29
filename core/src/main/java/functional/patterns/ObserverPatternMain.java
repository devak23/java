package functional.patterns;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObserverPatternMain {

    public static void main(String[] args) {
        FireStation fireStation = new FireStation();
        fireStation.registerFireStation((String address) -> {
            if (address.contains("BrookHaven")) {
                log.info("BrookHaven firestation will answer the fire call");
            }
        });

        fireStation.registerFireStation((String address) -> {
            if (address.contains("Vinings")) {
                log.info("Vinings firestation will answer the fire call");
            }
        });

        fireStation.registerFireStation((String address) -> {
            if (address.contains("Decatur")) {
                log.info("Decatur firestation will answer the fire call");
            }
        });

        fireStation.notifyFireStations("Fire alert: WestHaven At Vinings 5901 Suffex Green Ln Atlanta");
    }
}

// OUTPUT:
// 02:55:30.488 [main] INFO functional.patterns.ObserverPatternMain -- Vinings firestation will answer the fire call