package com.ak.learning.concurrency.producerconsumer.threadsafestack;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class TestThreadSafeStack {
    @Test
    public void testAnEmptyStackCanBeCreated() {
        ThreadSafeStack<String> nameStack = new ThreadSafeStack<>();
        Assert.assertNotNull(nameStack);
    }

    @Test
    public void testAThreadSafeStackCanStoreNames() {
        ThreadSafeStack<String> nameStack = new ThreadSafeStack<>();
        Assert.assertNotNull(nameStack);
        nameStack.push("Soham");
        nameStack.push("Abhay");
        Assert.assertEquals(nameStack.count(), 2);
    }

    @Test
    public void testAThreadSafeStackCanStoreIntegers() {
        ThreadSafeStack<Integer> ageStack = new ThreadSafeStack<>();
        Assert.assertNotNull(ageStack);
        ageStack.push(4);
        ageStack.push(38);
        Assert.assertEquals(ageStack.count(), 2);
    }

    @Test
    public void testAThreadSafeStackCanStoreDates() {
        ThreadSafeStack<Date> dobStack = new ThreadSafeStack<>();
        Assert.assertNotNull(dobStack);
        Calendar cal = Calendar.getInstance();
        cal.set(2013,03, 14);
        dobStack.push(cal.getTime());
        cal = Calendar.getInstance();
        cal.set(1978, 8, 23);
        dobStack.push(cal.getTime());
        Assert.assertEquals(dobStack.count(), 2);
    }
}
