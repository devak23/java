package com.ak.learnings;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class RankCacheImplTest {
    private RankCache classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = RankCacheImpl.getInstance();
        classUnderTest.setSourceFile("/StudentData.txt");
    }

    @Test
    public void testMapInitializationShouldNotContainMoreThanTwentyElements() {
        classUnderTest.readFromCache("1");
        assertEquals(5, classUnderTest.size());
    }

    @Test
    public void testMapPutAfterReadEvictsLeastRecentlyUsedEntry() throws InterruptedException {
        classUnderTest.readFromCache("1");
        TimeUnit.SECONDS.sleep(10);
        invokeRead("2", 3);
        invokeRead("4", 5);
        invokeRead("5", 2);
        invokeRead("1", 10);

        StudentResult chetan = new StudentResult().setMarks(94).setName("Chetan").setPercentage("94%").setRank(7);
        classUnderTest.addToCache(chetan);
        StudentResult abhay = classUnderTest.readFromCache("3");
        assertNull(abhay);
    }

    private void invokeRead(String rank, int number) {
        for (int i = 0; i < number; i++) {
            StudentResult result = classUnderTest.readFromCache(rank);
            assertNotNull(result);
            assertEquals(Integer.parseInt(rank), result.getRank());
        }
    }
}