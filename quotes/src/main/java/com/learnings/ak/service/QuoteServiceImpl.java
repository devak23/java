package com.learnings.ak.service;


import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Random;

@Service
public class QuoteServiceImpl implements QuoteService {
    private String[] quotes;
    private String[] messages;

    @Override
    public String getRandomQuote() {
        return getMessage(quotes);
    }

    @Override
    public String getUserMessages(String name) {
        return name + ": " + getMessage(messages);
    }

    private String getMessage(String[] quotes) {
        if (quotes == null || quotes.length == 0) {
            return "No Quotes loaded!";
        }

        Random rand = new Random();
        return quotes[rand.nextInt(quotes.length)];
    }


    @PostConstruct
    public void init() {
        quotes = new String[]{
                "Be afraid of the calmest person in the room"
                , "When Life gets harder callenge yourself to become stronger"
                , "Never stop dreaming, life can go from zero to hundred very quickly"
                , "People come and go. That's life"
                , "Never second guess a strong feeling that you have. Trust your gut."
                , "Do everything with a good heart and expect nothing in return, and you will never be disappointed"
                , "The world is changed by your example; not by your opinion."
                , "The most important thing in communication is hearing what isn't said."
                , "Never be afraid of change. You may lose something good, but you may gain something even better"
                , "You are rich when content and happy when what you have"
                , "Tough situation builds strong people"
                , "I care. I always care. That is my problem"
                , "True love has a habbit of coming back"
                , "I don't go crazy. I'm crazy. I go normal from time to time"
                , "Once you make a decision, universe conspires to make it happen"
                , "One of the most beautiful things we can do is to help one another. Kindess doesn't cost a thing"
                , "Don't compare your Chapter 1 to someone else's Chapter 20"
        };

        messages = new String[]{
                "I'm feeling great today!"
                , "Well, I've come here often, and I find these quotes very inspiring! what do you think?"
                , "I wish I could have said something that was profound"
                , "What a great quote! Who said it though?"
                , "I dont even know how people come up with these thoughts!"
                , "I have a quote of my own. Care to listen to?"
                , "Wish i could have a live person telling me this instead of reading it"
        };
    }
}
