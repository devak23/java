package com.ak.learning.patterns.observer;


public class TestClient {
    
    public static void main(String... args) {
        NewsAgency newsAgency = new NewsAgency("AajTak News Agency");
        RadioChannel channel = new RadioChannel();

        newsAgency.addObserver(channel);

        newsAgency.addNews("Life found on Mars");
        newsAgency.addNews("India launches the lightest satellite in space");
        newsAgency.addNews("2 terrorists killed in Srinagar");
    }
}
