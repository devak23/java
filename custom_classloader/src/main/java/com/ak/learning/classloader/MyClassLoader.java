package com.ak.learning.classloader;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

// Implementation of custom classloader begins with extending the Classloader.
// All that's needed to implement it is to provide an implementation of the <code>loadClass</code>
// method.
public class MyClassLoader extends ClassLoader {
    private Map<String, Class<?>> classCache;
    private String jarPath;

    public MyClassLoader() {
        // registering with the parent classloader in case if this classloader is
        // not able to load the class.
        super(Thread.currentThread().getClass().getClassLoader());
        classCache = new HashMap<>();
    }

    public MyClassLoader(String jarPath) {
        this();
        this.jarPath = jarPath;
    }

    public Class findClass(String name) throws ClassNotFoundException {
        Class<?> clazz = null;

        if (isEmpty(name)) {
            throw new ClassNotFoundException("Class cannot be loaded as it is empty");
        }

        // as for the typical operation of a loadClass method,
        // 1. check if the class is already loaded in the cache. If so, return it
        if (classCache.containsKey(name)) {
            return classCache.get(name);
        }

        // 2. try to load the class from the system classloader if it exists
        try {
            clazz = findSystemClass(name);
        } catch (ClassNotFoundException cnfe) {
            //cnfe.printStackTrace();
        }
        // 3. next up, try to load the class by reading bytes and defining a class
        // we are also checking the class that this classloader will invoke should
        // of some specific package
        if (clazz == null) {
            System.out.println("Attempting to load the class: " + name);
            String file = name.replace(".", File.separator) + ".class";
            byte buffer[] = null;
            try {
                // read the class file into byte array (buffer)
                buffer = this.readClass(file);
                // defineClass method is a JVM level implementation that takes in
                // the byte array and turns into a class object
                clazz = defineClass(name, buffer, 0, buffer.length);
                classCache.put(name, clazz);
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        }

        return clazz;
    }
    @Override
    public synchronized Class<?> loadClass(String name) throws ClassNotFoundException {
        return findClass(name);
    }

    // this method either reads it from the path or from the jar file
    private byte[] readClass(String file) throws IOException {
        // go through the list of all the folders mentioned in the jarPath variable.
        // if every entry is a file, open the jar file and try to load the class
        // if the entry is a directory, try to load the class from the directory
        if (isEmpty(jarPath)) {
            return _doReadFile(file);
        } else {
            return _doReadJar(file);
        }
    }

    // convert the class file into a byte array
    private byte[] _doReadFile(String fileName) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        int size = is.available();
        byte buffer[] = new byte[size];
        DataInputStream dip = new DataInputStream(is);
        dip.readFully(buffer);
        is.close();
        return buffer;
    }

    // a helper to check if the passed in name is null or empty string
    private boolean isEmpty(String name) {
        return name == null || name.trim().length() == 0;
    }

    // convert a class in the jar file to byte array
    private byte[] _doReadJar(String file) throws IOException {
        JarFile jarFile = new JarFile(jarPath);
        JarEntry entry = jarFile.getJarEntry(file);
        if (entry == null) {
            return null;
        }

        InputStream is = jarFile.getInputStream(entry);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int nextValue = 0;
        while ((nextValue = is.read()) != -1) {
            baos.write(nextValue);
        }

        return baos.toByteArray();
    }
}
/**
 * Problems I faced:
 * 1. I forgot to override the findClass() method because of which the java.lang.Object was not getting loaded. I
 * couldn't understand the concept well but it threw java.lang.SecurityException: Prohibited package name: java.lang
 * 2. I forgot to add a default constructor in the Greeter method.
 *
 */