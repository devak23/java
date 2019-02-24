package com.learnings.ak.config;

import com.learnings.ak.model.QuoteResponse;
import com.learnings.ak.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduleConfig {
    @Autowired
    private QuoteService quoteService;

    @Autowired
    SimpMessagingTemplate template;

    @Scheduled(fixedDelay = 4000)
    public void sendRandomMessages() {
        String message = quoteService.getRandomQuote();
        template.convertAndSend("/topic/quote", new QuoteResponse().setContent(message));
    }
}
