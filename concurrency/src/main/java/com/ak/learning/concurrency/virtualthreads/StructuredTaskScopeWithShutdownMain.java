package com.ak.learning.concurrency.virtualthreads;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.logging.Logger;

import static com.ak.learning.concurrency.virtualthreads.InvokeAllMain.fetchTester;

// In the previous program (StructuredTaskScopeMain), we used StructuredTaskScope to solve a task via a single virtual
// thread (a single Subtask). Basically, we fetched the tester with ID 1 from our server (we had to wait until this one
// was available). In this program, let’s assume that we still need a single tester, but not mandatorily the one with
// ID 1. This time, it could be any of IDs 1, 2, or 3. We simply take the first one that is available from these three,
// and we cancel the other two requests.
public class StructuredTaskScopeWithShutdownMain {
    private static final Logger LOGGER = Logger.getLogger(StructuredTaskScopeWithShutdownMain.class.getName());

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tT] [%4$-7s] %5$s %n");

        TestingTeam team = buildTestingTeamWithAtLeastOneSuccess();
        LOGGER.info(STR."At least one success: \{team}");

        TestingTeam team2 = buildTestingTeamWithAllSuccess();
        LOGGER.info(STR."Result: \{team2}");
    }

    // Here we intentionally replaced ID 3 with Integer.MAX_VALUE. Since there is no tester with this ID, the server
    // will throw UserNotFoundException. This means that the states of the subtasks will reveal that the third subtask
    // has failed
    private static TestingTeam buildTestingTeamWithAllSuccess() {
        try (StructuredTaskScope.ShutdownOnFailure scope = new StructuredTaskScope.ShutdownOnFailure()) {
            StructuredTaskScope.Subtask subtask1 = scope.fork(() -> fetchTester(1));
            StructuredTaskScope.Subtask subtask2 = scope.fork(() -> fetchTester(2));
            StructuredTaskScope.Subtask subtask3 = scope.fork(() -> fetchTester(Integer.MAX_VALUE)); // will cause exception

            scope.join();
            LOGGER.info("subtask1-state: " + subtask1.state());
            LOGGER.info("subtask2-state: " + subtask2.state());
            LOGGER.info("subtask3-state: " + subtask3.state());

            Optional<Throwable> exception = scope.exception();
            if (exception.isEmpty()) {
                return new TestingTeam((String) subtask1.get(), (String) subtask2.get(), (String) subtask3.get());
            } else {
                return new TestingTeam(exception.get().getMessage());
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    // OUTPUT:
    // [21:53:16] [INFO   ] sleeping arbitrarily: VirtualThread[#42]/runnable@ForkJoinPool-1-worker-1
    // [21:53:16] [INFO   ] sleeping arbitrarily: VirtualThread[#44]/runnable@ForkJoinPool-1-worker-2
    // [21:53:16] [INFO   ] sleeping arbitrarily: VirtualThread[#43]/runnable@ForkJoinPool-1-worker-4
    // [21:53:19] [INFO   ] subtask1-state: SUCCESS
    // [21:53:19] [INFO   ] subtask2-state: SUCCESS
    // [21:53:19] [INFO   ] subtask3-state: FAILED
    // [21:53:19] [INFO   ] Result: TestingTeam(testers=[Code: 404])


    private static TestingTeam buildTestingTeamWithAtLeastOneSuccess() {
        try (StructuredTaskScope.ShutdownOnSuccess scope = new StructuredTaskScope.ShutdownOnSuccess()) {
            StructuredTaskScope.Subtask<String> subtask1 = scope.fork(() -> fetchTester(1));
            StructuredTaskScope.Subtask<String> subtask2 = scope.fork(() -> fetchTester(2));
            StructuredTaskScope.Subtask<String> subtask3 = scope.fork(() -> fetchTester(3));
            scope.join();

            LOGGER.info(() -> "Subtask-1 state: " + subtask1.state());
            LOGGER.info(() -> "Subtask-2 state: " + subtask2.state());
            LOGGER.info(() -> "Subtask-3 state: " + subtask3.state());

            return new TestingTeam((String) scope.result());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

// Here, we fork three subtasks (threads) that will compete with each other to complete. The first subtask (thread) that
// completes successfully wins and returns. The result() method returns this result (if none of the subtasks (threads)
// complete successfully, then it throws an ExecutionException).

// OUTPUT:
// [21:19:38] [INFO   ] sleeping arbitrarily: VirtualThread[#30]/runnable@ForkJoinPool-1-worker-4
// [21:19:38] [INFO   ] sleeping arbitrarily: VirtualThread[#22]/runnable@ForkJoinPool-1-worker-1
// [21:19:38] [INFO   ] sleeping arbitrarily: VirtualThread[#25]/runnable@ForkJoinPool-1-worker-3
// [21:19:39] [INFO   ] Subtask-1 state: UNAVAILABLE
// [21:19:39] [INFO   ] Subtask-2 state: SUCCESS
// [21:19:39] [INFO   ] Subtask-3 state: UNAVAILABLE
// [21:19:39] [INFO   ] Team: TestingTeam(testers=[{"data":{"id":2,"email":"janet.weaver@reqres.in","first_name":"Janet","last_name":"Weaver","avatar":"https://reqres.in/img/faces/2-image.jpg"},"support":{"url":"https://reqres.in/#support-heading","text":"To keep ReqRes free, contributions towards server costs are appreciated!"}}])

// You just create the scope, fork your subtasks, call join(), and collect the result. So the scope is really business
// focused. A task that completes exceptionally under the ShutdownOnSuccess umbrella will never be chosen to produce a
// result. However, if all tasks complete exceptionally, then we will get an ExecutionException that wraps the exception
// (i.e., the cause) of the first completed task.