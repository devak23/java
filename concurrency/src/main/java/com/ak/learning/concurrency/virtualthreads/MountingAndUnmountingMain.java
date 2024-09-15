package com.ak.learning.concurrency.virtualthreads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;


// The operation of assigning a virtual thread to a platform thread is called mounting. The operation of unassigning a
// virtual thread from the platform thread is called unmounting. The platform thread running the assigned virtual thread
// is called a carrier thread.

// Between the virtual threads and the platform threads, there is a one-to-many association. Nevertheless, the JVM
// schedules virtual threads to run on platform threads in such a way that only one virtual thread runs on a platform
// thread at a time. When the JVM assigns a virtual thread to a platform thread, the so-called stack chunk object of the
// virtual thread is copied from the heap memory on the platform thread.

// If the code running on a virtual thread encounters a blocking (I/O) operation that should be handled by the JVM,
// then the virtual thread is released by copying its stack chunk object back into the heap (this operation of copying
// the stack chunk between the heap memory and platform thread is the cost of blocking a virtual thread - this is much
// cheaper than blocking a platform thread). Meanwhile, the platform thread can run other virtual threads. When the
// blocking (I/O) of the released virtual thread is done, JVM reschedules the virtual thread for execution on a platform
// thread. This can be the same platform thread or another one.

public class MountingAndUnmountingMain {
    private static final Logger LOGGER = Logger.getLogger(MountingAndUnmountingMain.class.getSimpleName());
    private static final int NUMBER_OF_TASKS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        LOGGER.info("# of processors: " + NUMBER_OF_TASKS);
        Runnable taskr = () ->  LOGGER.info("Runnable: " + Thread.currentThread());

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < NUMBER_OF_TASKS + 1; i++) {
                executor.submit(taskr);
            }
        }
    }

    // we create a number of virtual threads equal to the number of available cores + 1. On my machine, I have 8 cores
    // (so 8 carriers), and each of them carries a virtual thread. Since we have + 1, a carrier will work twice. The
    // output reveals this scenario

    // OUTPUT:
    // INFO: # of processors: 8
    // Sept 15, 2024 5:47:41 PM com.ak.learning.concurrency.virtualthreads.MountingAndUnmountingMain lambda$main$0
    // INFO: Runnable: VirtualThread[#25]/runnable@ForkJoinPool-1-worker-3
    // Sept 15, 2024 5:47:41 PM com.ak.learning.concurrency.virtualthreads.MountingAndUnmountingMain lambda$main$0
    // INFO: Runnable: VirtualThread[#22]/runnable@ForkJoinPool-1-worker-1
    // Sept 15, 2024 5:47:41 PM com.ak.learning.concurrency.virtualthreads.MountingAndUnmountingMain lambda$main$0
    // INFO: Runnable: VirtualThread[#24]/runnable@ForkJoinPool-1-worker-2
    // Sept 15, 2024 5:47:41 PM com.ak.learning.concurrency.virtualthreads.MountingAndUnmountingMain lambda$main$0
    // INFO: Runnable: VirtualThread[#26]/runnable@ForkJoinPool-1-worker-4
    // Sept 15, 2024 5:47:41 PM com.ak.learning.concurrency.virtualthreads.MountingAndUnmountingMain lambda$main$0
    // INFO: Runnable: VirtualThread[#27]/runnable@ForkJoinPool-1-worker-5
    // Sept 15, 2024 5:47:41 PM com.ak.learning.concurrency.virtualthreads.MountingAndUnmountingMain lambda$main$0
    // INFO: Runnable: VirtualThread[#29]/runnable@ForkJoinPool-1-worker-4
    // Sept 15, 2024 5:47:41 PM com.ak.learning.concurrency.virtualthreads.MountingAndUnmountingMain lambda$main$0
    // INFO: Runnable: VirtualThread[#28]/runnable@ForkJoinPool-1-worker-2
    // Sept 15, 2024 5:47:41 PM com.ak.learning.concurrency.virtualthreads.MountingAndUnmountingMain lambda$main$0
    // INFO: Runnable: VirtualThread[#31]/runnable@ForkJoinPool-1-worker-5
    // Sept 15, 2024 5:47:41 PM com.ak.learning.concurrency.virtualthreads.MountingAndUnmountingMain lambda$main$0
    // INFO: Runnable: VirtualThread[#30]/runnable@ForkJoinPool-1-worker-1


    // As you can see the following threads get reused
    // INFO: Runnable: VirtualThread[#22]/runnable@ForkJoinPool-1-worker-1
    // INFO: Runnable: VirtualThread[#30]/runnable@ForkJoinPool-1-worker-1

    // INFO: Runnable: VirtualThread[#26]/runnable@ForkJoinPool-1-worker-4
    // INFO: Runnable: VirtualThread[#29]/runnable@ForkJoinPool-1-worker-4

    // INFO: Runnable: VirtualThread[#27]/runnable@ForkJoinPool-1-worker-5
    // INFO: Runnable: VirtualThread[#31]/runnable@ForkJoinPool-1-worker-5

    // INFO: Runnable: VirtualThread[#24]/runnable@ForkJoinPool-1-worker-2
    // INFO: Runnable: VirtualThread[#28]/runnable@ForkJoinPool-1-worker-2
}
