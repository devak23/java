package com.ak.learning.patterns.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class NewsAgency extends Observable {
    private String name;
    private List<Observer> channels = new ArrayList<>();

    public NewsAgency(String name) {
        this.name = name;
    }

    public void addNews(String newsItem) {
        notifyObservers(newsItem);
    }

    @Override
    public void notifyObservers(Object newsItem) {
        for(Observer observer : channels) {
            observer.update(this, newsItem);
        }
    }

    @Override
    public synchronized void addObserver(Observer observer) {
        channels.add(observer);
    }

    public String getName() {
        return name;
    }
}
