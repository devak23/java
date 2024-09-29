package functional.patterns;

import functional.spec.FireObserver;
import functional.spec.FireStationRegister;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TraditionalObserverPatternMain {

    // Each local fire station (observer) implements this interface and decides whether to
    //extinguish the fire or not in the fire() implementation

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

    // The Fire Station headquarters (subject). The fire station headquarters can be the subject, and the local fire
    // stations can be the observers. When a fire has started, the fire station headquarters notifies all local fire
    // stations and sends them the address where the fire is taking place. Each observer analyzes the received address
    // and, depending on different criteria, decides whether to extinguish the fire or not.
    static class FireStation implements FireStationRegister {
        private List<FireObserver> observers = new ArrayList<>();

        @Override
        public void registerFireStation(FireObserver fo) {
            observers.add(fo);
        }

        @Override
        public void notifyFireStations(String address) {
            observers.forEach(observer -> observer.fire(address));
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