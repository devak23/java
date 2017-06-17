package com.ak.learning.classloader;

import com.ak.learning.Greeter;

public class TestClient {
    public static void main(String[] args) throws Exception {
        MyClassLoader mcl1 = new MyClassLoader();
        Class<Greeter> clazz1 = (Class<Greeter>) mcl1.loadClass("com.ak.learning.Greeter");
        Greeter greeter1 = clazz1.newInstance();
        greeter1.setMessage("I was invoked by MyClassLoader1");
        System.out.println(greeter1.getMessage());

        MyClassLoader mcl2 = new MyClassLoader("/tmp/greeter.jar");
        Class<Greeter> clazz2 = (Class<Greeter>) mcl2.loadClass("com.ak.learning.Greeter");
        Greeter greeter2 = clazz2.newInstance();
        greeter2.setMessage("I was invoked by MyClassLoader2");
        System.out.println(greeter2.getMessage());
    }
}
