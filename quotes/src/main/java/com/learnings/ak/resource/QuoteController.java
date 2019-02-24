package com.learnings.ak.resource;

import com.learnings.ak.model.Author;
import com.learnings.ak.model.QuoteResponse;
import com.learnings.ak.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class QuoteController {
    @Autowired
    private QuoteService quoteService;

    @MessageMapping("/author")
    @SendTo("/topic/user")
    QuoteResponse getQuote(Author author) {
        String message = quoteService.getUserMessages(author.getName());
        return new QuoteResponse().setContent(author.getName() + ":" + message);
    }
}
