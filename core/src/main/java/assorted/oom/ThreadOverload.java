package assorted.oom;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// Check how many threads are allowed by your machine. 10300 on my linux.
// This program will NOT generate the hprof file because the the native thread
// created was not a part of the heap. http://bugs.java.com/view_bug.do?bug_id=6784422
// http://www.oracle.com/technetwork/java/javase/memleaks-137499.html#gbywc
public class ThreadOverload {
    public static void main(String[] args) {
        AtomicInteger number = new AtomicInteger();
        for (;;) {
            new Thread(() -> {
                System.out.println(number.incrementAndGet());
                try {
                    TimeUnit.SECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}
