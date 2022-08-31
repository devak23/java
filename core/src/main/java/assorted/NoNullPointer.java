package assorted;

// When a static method is invoked using an object handle, the object handle is usually discarded
// so it doesn't matter if the object handle is null or any valid one.
public class NoNullPointer {

    public static void main(String[] args) {
        NoNullPointer nnp = null;
        nnp.invokeMe();
        System.out.println("Good bye!");
    }

    public static void invokeMe() {
        System.out.println("Hey!... see ... No NullPointerException :)");
    }
}
