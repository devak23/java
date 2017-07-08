package com.ak.learning.concurrency.callablesandfutures;

import com.ak.learning.concurrency.callablesandfutures.simplecounter.CounterSetterTask;
import com.ak.learning.concurrency.callablesandfutures.simplecounter.SimpleCounter;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * If you run this test case a few times, you'll see it passes sometimes and fails sometimes.
 * When it fails, it is because the assertion thread (main thread) sets the value and checks
 * it with a slight delay due to the 'System.out.println("Now to check if it still has 200");'
 * In the CPU scheduler, if the second executor thread gets the time slice, it resets it back to 100.
 * When the test passes, the Main thread gets the entire CPU time slice to set the value, print the
 * message and assert the value.
 * The example only demonstrates the need for synchronizing the shared resource (SimpleCounter in
 * this case)
 */
public class TestSharedState {

    @Test
    public void testSharedState() throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        SimpleCounter c = new SimpleCounter();
        pool.execute(new CounterSetterTask(c));
        System.out.println("Setting the value to 200");
        c.setValue(200);
        System.out.println("Now to check if it still has 200");
        Assert.assertEquals(200, c.getValue());
    }
}
