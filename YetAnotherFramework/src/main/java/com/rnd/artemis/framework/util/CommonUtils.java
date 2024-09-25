package com.rnd.artemis.framework.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CommonUtils {
    public static final Random random = new Random(1729);

    public static void introduceRandomDelay() {
        try {
            TimeUnit.SECONDS.sleep(random.nextInt(1,10));
        } catch (InterruptedException e) {
            log.error("Problem sleeping thread:", e);
        }
    }
}
