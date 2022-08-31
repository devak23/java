package assorted;

import java.util.Arrays;

public class CheckIfAnagrams {
    public static void main(String[] args) {
        String s1 = "home";
        String s2 = "omhe";

        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();

        Arrays.sort(arr1);
        Arrays.sort(arr2);

        System.out.println(Arrays.equals(arr1, arr2));
        System.out.println(s1);
        System.out.println(s2);
    }
}
