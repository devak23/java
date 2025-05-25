package generics;

import java.util.ArrayList;
import java.util.List;

public class ReifiableMain {

    public static void main(String[] args) {
        // Reifiable Type
        Class<?> stringClass = String.class;

        // Non reifiable Class
        List<String> list = new ArrayList<>();
        Class<?> listClass = list.getClass();

        String typeName = stringClass.getName();
        System.out.println(STR."Reifiable class: \{typeName}");

        typeName = listClass.getName();
        System.out.println(STR."Non Reifiable class: \{typeName}");

    }
}

// OUTPUT:
// Reifiable class: java.lang.String
// Non Reifiable class: java.util.ArrayList


// Point to note in this example is - even though the list is of type String i.e List<String>
// yet the SOP statement will always say ArrayList and not ArrayList<String>. This is because
// the generic information is lost in runtime. That's also called as type erasure. The exact
// opposite is Reifiction where the runtime is able to understand the type of the variable.