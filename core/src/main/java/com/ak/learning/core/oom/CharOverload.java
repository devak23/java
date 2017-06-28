package com.ak.learning.core.oom;

public class CharOverload {
    public static void main(String[] args) {
        char[] tooBigArray = new char[90000000]; //500000000 causes OOM
        System.out.println("length = " + tooBigArray.length);
        String bigString = new String(tooBigArray);
        System.out.println("length of string = " + bigString.length());
        StringBuilder bigBuilder = new StringBuilder(bigString);
        System.out.println("length of builder = " + bigBuilder.length());
        String newString = bigBuilder.toString();
        System.out.println("length of new String = " + newString.length());
        
        // Running this program in eclipse causes OOM with java_pid10528.hprof generated.
    }
}
