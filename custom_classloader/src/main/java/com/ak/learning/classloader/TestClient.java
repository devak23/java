package com.ak.learning.classloader;

import java.lang.reflect.Method;

public class TestClient {
    public static void main(String[] args) throws Exception {
        MyClassLoader mcl1 = new MyClassLoader();
        Class<?> clazz1 = mcl1.loadClass("com.ak.learning.Greeter");
        Object greeter1 = clazz1.newInstance();
        Method getMessageMethod1 = clazz1.getDeclaredMethods()[0];
        String message1 = (String) getMessageMethod1.invoke(greeter1,"I was invoked by MyClassLoader1");
        System.out.println(message1);

        System.out.println("================ trying to read a jar file ==============");

        MyClassLoader mcl2 = new MyClassLoader("/tmp/greeter.jar");
        Class<?> clazz2 = mcl2.loadClass("com.ak.learning.Greeter");
        Object greeter2 = clazz2.newInstance();
        Method setMessageMethod2 = clazz2.getDeclaredMethods()[0]; // remember, this is from the jar which has the getter and setter both
        Method getMessageMethod2 = clazz2.getDeclaredMethods()[1];
        setMessageMethod2.invoke(greeter2, "I was invoked by MyClassLoader2");
        String message2 = (String) getMessageMethod2.invoke(greeter2);
        System.out.println(message2);
    }
}
