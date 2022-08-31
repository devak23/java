package assorted;

public class BrokenString {
    public static void main(String[] args) {
        String s1 = "noël";
        System.out.println(s1);
        System.out.println("s1.length(): " + s1.length());
        System.out.println("s1.codePoints().count(): " + s1.codePoints().count());
        System.out.println("s1.codePointCount(0, s1.length()): " + s1.codePointCount(0, s1.length()));
        System.out.println("reversing noël = " + new StringBuilder(s1).reverse());
        System.out.println("-------------- Printing character information -----------------");
        s1.codePoints().forEach(point -> {
            System.out.print((char)point);
            System.out.println( "..." + point);
        });

        String eWithUmlaut = "e\u0308";
        System.out.println("eWithUmlaut: " + eWithUmlaut);
        System.out.println("eWithUmlaut.length(): " + eWithUmlaut.length());

        String happyAndSadCats = "\uD83D\uDE38\uD83D\uDE3E";
        System.out.println(happyAndSadCats);
        System.out.println(happyAndSadCats.length());
        System.out.println(new StringBuilder(happyAndSadCats).reverse());
    }
}
