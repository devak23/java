package functional.spec;


// we need to register these observers to be notified by the subject. In other words, each local fire station needs
// to be registered as an observer to the fire station headquarters (subject). For this, we declare another
// interface that defines the subject contract for registering and notifying its observers

public interface FireStationRegister {

    void registerFireStation(FireObserver fo);
    void notifyFireStations(String address);
}
