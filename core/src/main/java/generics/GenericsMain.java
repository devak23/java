package generics;

public class GenericsMain {
    public static void main(String[] args) {
        Tuple<String, Integer> abhay = new Tuple<>("Abhay", 45);
        Tuple<String, Integer> soham = new Tuple<>("Soham", 12);

        System.out.printf("Name: %s, Age: %d\n%n", abhay.getOne(), abhay.getTwo());
        System.out.printf("Name: %s, Age: %d\n%n", soham.getOne(), soham.getTwo());

        print(new String[]{"abhay", "soham"});
        print(new Integer[]{45, 12});

        System.out.printf("smallest in name = %s\n", min(new String[]{"abhay", "soham"}));
        System.out.printf("smallest in age = %s\n", min(new Integer[]{45, 12}));
    }

    public static <E> void print(E[] array) {
        for(E e : array) {
            System.out.println(e);
        }
    }

    public static <E extends Comparable<E>> E min(E[] array) {
        E smallest = array[0];
        for(E e : array) {
            if (e.compareTo(smallest) < 0) {
                smallest = e;
            }
        }
        return smallest;
    }
}
