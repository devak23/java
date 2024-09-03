package concurrentmodifificaiton;

import java.util.Iterator;

public class WithList {
    public static void main(String[] args) {
        Person  abhay = new Person();
        abhay.getFriends().add("Meenakshi");
        abhay.getFriends().add("Suhas");
        abhay.getFriends().add("Saif");
        abhay.getFriends().add("Abrar");
        abhay.getFriends().add("Kavita");
        abhay.getFriends().add("Varkha");
        abhay.getFriends().add("Dhiren");
        abhay.getFriends().add("Nimisha");
        abhay.getFriends().add("Rupali");

        //safeRemoval(abhay);
        unsafeRemoval(abhay);
    }

    private static void unsafeRemoval(Person person) {
        for(String friend : person.getFriends()) {
            if (friend.startsWith("A")) {
                person.getFriends().remove(friend);
            } else {
                System.out.println("Found friend: " + friend);
            }
        }
    }

    private static void safeRemoval(Person person) {
        Iterator<String> iterator = person.getFriends().iterator();
        while (iterator.hasNext()) {
            String friend = iterator.next();
            if (friend.startsWith("A")) {
                iterator.remove();
            } else {
                System.out.println("found friend: " + friend);
            }
        }
    }
}
