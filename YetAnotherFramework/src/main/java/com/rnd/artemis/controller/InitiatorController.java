package com.rnd.artemis.controller;

import com.rnd.artemis.framework.model.Data;
import com.rnd.artemis.framework.processors.BaseProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/initiate")
@RequiredArgsConstructor
public class InitiatorController {
    private ApplicationContext applicationContext;

    @GetMapping
    public String status() {
        log.info("Thread: {}", Thread.currentThread());
        return "Running";
    }

    @PostMapping
    public void initiate(@RequestBody Data data) {
        BaseProcessor bean = (BaseProcessor) applicationContext.getBean(BaseProcessor.QUALIFIER);
        bean.execute(data);
    }
}
