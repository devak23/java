package com.ak.learning.concurrency.virtualthreads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import static com.ak.learning.concurrency.virtualthreads.InvokeAllMain.fetchTester;

public class TaskStateMain {
    private static final Logger LOGGER = Logger.getLogger(TaskStateMain.class.getName());

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tT] [%4$-7s] %5$s %n");

        TestingTeam testingTeam = buildTestingTeam();
        LOGGER.info("testingTeam: " + testingTeam);
    }

    private static TestingTeam buildTestingTeam() throws InterruptedException {
        List<String> testers = new ArrayList<>();

        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {

            List<Future<String>> futures = executorService.invokeAll(
                    List.of(() -> fetchTester(Integer.MAX_VALUE)
                            , () -> fetchTester(2), () -> fetchTester(Integer.MAX_VALUE))
            );

            futures.forEach(future -> {

                LOGGER.info(() -> "Analyzing " + future + " state ...");

                switch (future.state()) {
                    case RUNNING -> throw new IllegalStateException("Future is still in running state?");
                    case SUCCESS -> {
                        LOGGER.info(() -> "Result: " + future.resultNow());
                        testers.add(future.resultNow());
                    }
                    case FAILED -> LOGGER.severe(() -> "Exception: " + future.exceptionNow().getMessage());
                    case CANCELLED -> LOGGER.info("Cancelled?!");
                }
            });
        }

        return new TestingTeam(testers.toArray(String[]::new));
    }
}

// We know that when the execution reaches the switch block, the Future objects should be completely normal or
// exceptional. So if the current Future state is RUNNING, then this is a really weird situation (possibly a bug), and
// we throw an IllegalStateException. Next, if the Future state is SUCCESS (fetchTester(2)), then we have a result that
// can be obtained via resultNow(). This method was added in JDK 19, and it is useful when we know that we have a result
// The resultNow() method returns immediately without waiting (as get()). If the state is FAILED (fetchTester(Integer.MAX_VALUE)),
// then we log the exception via exceptionNow(). This method was also added in JDK 19, and it returns immediately the
// underlying exception of a failed Future. Finally, if the Future was canceled, then there is nothing to do. We just
// report it in the log.


// OUTPUT:
// [09:06:27] [INFO   ] sleeping arbitrarily: VirtualThread[#22]/runnable@ForkJoinPool-1-worker-1
// [09:06:27] [INFO   ] sleeping arbitrarily: VirtualThread[#24]/runnable@ForkJoinPool-1-worker-2
// [09:06:27] [INFO   ] sleeping arbitrarily: VirtualThread[#25]/runnable@ForkJoinPool-1-worker-3
// [09:06:30] [INFO   ] Analyzing java.util.concurrent.ThreadPerTaskExecutor$ThreadBoundFuture@3d646c37[Completed exceptionally: com.ak.learning.concurrency.virtualthreads.UserNotFoundException: Code: 404] state ...
// [09:06:30] [SEVERE ] Exception: Code: 404
// [09:06:30] [INFO   ] Analyzing java.util.concurrent.ThreadPerTaskExecutor$ThreadBoundFuture@5ce65a89[Completed normally] state ...
// [09:06:30] [INFO   ] Result: {"data":{"id":2,"email":"janet.weaver@reqres.in","first_name":"Janet","last_name":"Weaver","avatar":"https://reqres.in/img/faces/2-image.jpg"},"support":{"url":"https://reqres.in/#support-heading","text":"To keep ReqRes free, contributions towards server costs are appreciated!"}}
// [09:06:30] [INFO   ] Analyzing java.util.concurrent.ThreadPerTaskExecutor$ThreadBoundFuture@3eb07fd3[Completed exceptionally: com.ak.learning.concurrency.virtualthreads.UserNotFoundException: Code: 404] state ...
// [09:06:30] [SEVERE ] Exception: Code: 404
// [09:06:30] [INFO   ] testingTeam: TestingTeam(testers=[{"data":{"id":2,"email":"janet.weaver@reqres.in","first_name":"Janet","last_name":"Weaver","avatar":"https://reqres.in/img/faces/2-image.jpg"},"support":{"url":"https://reqres.in/#support-heading","text":"To keep ReqRes free, contributions towards server costs are appreciated!"}}])
