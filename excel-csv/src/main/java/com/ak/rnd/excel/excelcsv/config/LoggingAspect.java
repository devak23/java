package com.ak.rnd.excel.excelcsv.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("@annotation(com.ak.rnd.excel.excelcsv.config.LogTime)")
    public Object executionTime(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        Object object = jp.proceed();
        long end = System.currentTimeMillis();
        log.info("Time taken by: {} = {} seconds.",
                jp.getSignature().toShortString(),
                ((end - start)/1000));
        return object;
    }
}
