package threads;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class FibonacciMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinTask<Integer> task = new FibonacciTask(35);
        ForkJoinPool pool = new ForkJoinPool();
        Integer result = pool.invoke(task);
        System.out.println(result);
        pool.shutdown();
    }
}
