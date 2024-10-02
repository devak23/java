package functional.model;

import functional.spec.FireObserver;
import functional.spec.FireStationRegister;

import java.util.ArrayList;
import java.util.List;

// The Fire Station headquarters (subject). The fire station headquarters can be the subject, and the local fire
// stations can be the observers. When a fire has started, the fire station headquarters notifies all local fire
// stations and sends them the address where the fire is taking place. Each observer analyzes the received address
// and, depending on different criteria, decides whether to extinguish the fire or not.

public class FireStation implements FireStationRegister {

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
