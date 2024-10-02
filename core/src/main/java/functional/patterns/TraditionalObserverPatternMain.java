package functional.patterns;

import functional.model.FireStation;
import functional.spec.FireObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TraditionalObserverPatternMain {

    // Each local fire station (observer) implements this interface and decides whether to
    // extinguish the fire or not in the fire() implementation

    static class BrookHavenFireStation implements FireObserver {

        @Override
        public void fire(String address) {
            if (address.contains("BrookHaven")) {
                log.info("BrookHaven fire station will go to extinguish this fire");
            }
        }
    }

    static class ViningsFireStation implements FireObserver {

        @Override
        public void fire(String address) {
            if (address.contains("Vinings")) {
                log.info("Vinings fire station will go to extinguish this fire");
            }
        }
    }
    static class DecaturFireStation implements FireObserver {

        @Override
        public void fire(String address) {
            if (address.contains("Decatur")) {
                log.info("Decatur fire station will go to extinguish this fire");
            }
        }
    }


    public static void main(String[] args) {
        FireStation fireStation = new FireStation();
        fireStation.registerFireStation(new ViningsFireStation());
        fireStation.registerFireStation(new DecaturFireStation());
        fireStation.registerFireStation(new BrookHavenFireStation());

        // Now, when a fire occurs, the fire station headquarters will notify all registered local fire stations:
        fireStation.notifyFireStations("Fire alert: WestHaven At Vinings 5901 Suffex Green Ln Atlanta");
    }

}


// OUTPUT:
// 02:47:56.390 [main] INFO functional.patterns.TraditionalObserverPatternMain -- Vinings fire station will go to extinguish this fire