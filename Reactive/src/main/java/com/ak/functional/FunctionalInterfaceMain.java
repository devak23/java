package com.ak.functional;

public class FunctionalInterfaceMain {

    public static void main(String[] args) {
        ToCaps tc = new ToCaps();
        System.out.println(tc.process("abhay"));

        StringProcessor toLower = String::toLowerCase;
        System.out.println(toLower.process("AN EXAMPLE"));
    }


}
