package com.ak.learning.concurrency.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class ExecutorFrameworkDemo {

    private static final List<String> NAMES = Arrays.asList("Einstein", "Newton", "Raman", "Bose", "Powel"
            , "Heisenberg", "Pauli", "Dirac", "Gauss", "Feynman", "Currie", "Fermat", "Avogadro", "Bernoulli"
    );

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorFrameworkDemo demo = new ExecutorFrameworkDemo();
        demo.runReport();
    }

    public void runReport() throws ExecutionException, InterruptedException {
        List<Future<String>> values = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(5);
        try {
            values = service.invokeAll(getAllReports());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        service.shutdown();
        System.out.println("Now iterating the result.");
        for (Future<String> f : values) {
            System.out.println(f.get());
        }
    }

    private List<Callable<String>> getAllReports() {
        List<Callable<String>> tasks = new ArrayList<>(10);
        for (String name: NAMES) {
            tasks.add(() -> {
                int seconds = new Random().nextInt(5);
                TimeUnit.SECONDS.sleep(seconds);
                String salutation = seconds % 2 == 0 ? "Hello " : "Hi,";
                return salutation + name + " (delay of " + seconds + " seconds)";
            });
        }
        return tasks;
    }
}
