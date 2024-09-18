package com.ak.learning.concurrency.virtualthreads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

import static com.ak.learning.concurrency.Utils.sleep;

public class ContextSwitchingMain {
    private static final Logger LOGGER = Logger.getLogger(ContextSwitchingMain.class.getName());
    private static final int MAX_THREADS = 10;
    static class PlatformThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable);
        }
    }

    static class VirtualThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable runnable) {
            return Thread.ofVirtual().unstarted(runnable);
        }
    }

    public static void doSomething(int index) {
        LOGGER.info(() -> index + ": started: " + Thread.currentThread());
        sleep(3);
        LOGGER.info(() -> index + ": ending: " + Thread.currentThread());
    }

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tT] [%4$-7s] %5$s %n");

        try (ExecutorService executorService = Executors.newThreadPerTaskExecutor(new VirtualThreadFactory())) {
            for (int i = 0; i < MAX_THREADS; i++) {
                int index = i;
                executorService.submit(() -> doSomething(index));
            }
        }
    }
}

// Output with Platform thread, you can see there is an association of the task number with the given thread before and after sleeping
// [08:32:09] [INFO   ] 7: started: Thread[#29,Thread-7,5,main] | sleep | [08:32:12] [INFO   ] 7: ending: Thread[#29,Thread-7,5,main]
// [08:32:09] [INFO   ] 5: started: Thread[#27,Thread-5,5,main] | sleep | [08:32:12] [INFO   ] 5: ending: Thread[#27,Thread-5,5,main]
// [08:32:09] [INFO   ] 0: started: Thread[#22,Thread-0,5,main] | sleep | [08:32:12] [INFO   ] 0: ending: Thread[#22,Thread-0,5,main]
// [08:32:09] [INFO   ] 9: started: Thread[#31,Thread-9,5,main] | sleep | [08:32:12] [INFO   ] 9: ending: Thread[#31,Thread-9,5,main]
// [08:32:09] [INFO   ] 6: started: Thread[#28,Thread-6,5,main] | sleep | [08:32:12] [INFO   ] 6: ending: Thread[#28,Thread-6,5,main]
// [08:32:09] [INFO   ] 8: started: Thread[#30,Thread-8,5,main] | sleep | [08:32:12] [INFO   ] 8: ending: Thread[#30,Thread-8,5,main]
// [08:32:09] [INFO   ] 1: started: Thread[#23,Thread-1,5,main] | sleep | [08:32:12] [INFO   ] 1: ending: Thread[#23,Thread-1,5,main]
// [08:32:09] [INFO   ] 3: started: Thread[#25,Thread-3,5,main] | sleep | [08:32:13] [INFO   ] 3: ending: Thread[#25,Thread-3,5,main]
// [08:32:09] [INFO   ] 4: started: Thread[#26,Thread-4,5,main] | sleep | [08:32:13] [INFO   ] 4: ending: Thread[#26,Thread-4,5,main]
// [08:32:09] [INFO   ] 2: started: Thread[#24,Thread-2,5,main] | sleep | [08:32:13] [INFO   ] 2: ending: Thread[#24,Thread-2,5,main]

// With Virtual threads, there is no association of the task with the given thread
// [08:37:00] [INFO   ] 2: started: VirtualThread[#25]/runnable@ForkJoinPool-1-worker-3 | sleep | [08:37:03] [INFO   ] 2: ending: VirtualThread[#25]/runnable@ForkJoinPool-1-worker-8
// [08:37:00] [INFO   ] 6: started: VirtualThread[#29]/runnable@ForkJoinPool-1-worker-7 | sleep | [08:37:03] [INFO   ] 6: ending: VirtualThread[#29]/runnable@ForkJoinPool-1-worker-4
// [08:37:00] [INFO   ] 7: started: VirtualThread[#30]/runnable@ForkJoinPool-1-worker-8 | sleep | [08:37:03] [INFO   ] 7: ending: VirtualThread[#30]/runnable@ForkJoinPool-1-worker-3
// [08:37:00] [INFO   ] 5: started: VirtualThread[#28]/runnable@ForkJoinPool-1-worker-6 | sleep | [08:37:03] [INFO   ] 5: ending: VirtualThread[#28]/runnable@ForkJoinPool-1-worker-5
// [08:37:00] [INFO   ] 4: started: VirtualThread[#27]/runnable@ForkJoinPool-1-worker-5 | sleep | [08:37:03] [INFO   ] 4: ending: VirtualThread[#27]/runnable@ForkJoinPool-1-worker-1
// [08:37:00] [INFO   ] 3: started: VirtualThread[#26]/runnable@ForkJoinPool-1-worker-4 | sleep | [08:37:03] [INFO   ] 3: ending: VirtualThread[#26]/runnable@ForkJoinPool-1-worker-7
// [08:37:00] [INFO   ] 1: started: VirtualThread[#24]/runnable@ForkJoinPool-1-worker-2 | sleep | [08:37:03] [INFO   ] 1: ending: VirtualThread[#24]/runnable@ForkJoinPool-1-worker-2
// [08:37:00] [INFO   ] 0: started: VirtualThread[#22]/runnable@ForkJoinPool-1-worker-1 | sleep | [08:37:03] [INFO   ] 0: ending: VirtualThread[#22]/runnable@ForkJoinPool-1-worker-2
// [08:37:00] [INFO   ] 9: started: VirtualThread[#33]/runnable@ForkJoinPool-1-worker-1 | sleep | [08:37:03] [INFO   ] 9: ending: VirtualThread[#33]/runnable@ForkJoinPool-1-worker-2
// [08:37:00] [INFO   ] 8: started: VirtualThread[#31]/runnable@ForkJoinPool-1-worker-4 | sleep | [08:37:03] [INFO   ] 8: ending: VirtualThread[#31]/runnable@ForkJoinPool-1-worker-3


// When the sleeping is over, the JVM schedules the virtual thread for execution and is mounted on another (it could
// also be the same) worker. This is thread context switching in action.