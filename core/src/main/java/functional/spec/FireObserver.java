package functional.spec;

// Observer pattern relies on an object (known as the subject) that automatically notifies its subscribers
// (known as observers) when certain events happen.

// For example, the fire station headquarters can be the subject, and the local fire stations
//can be the observers.

// All the local fire stations are grouped via an interface called FireObserver. This method defines a single abstract
// method that is invoked by the fire station headquarters (subject)

@FunctionalInterface
public interface FireObserver {
    void fire(String address);
}
