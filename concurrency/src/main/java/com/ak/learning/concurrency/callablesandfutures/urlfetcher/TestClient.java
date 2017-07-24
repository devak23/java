package com.ak.learning.concurrency.callablesandfutures.urlfetcher;

public class TestClient {
    public static void main(String[] args) {
        String urls[] = new String[] {
                "http://www.google.com"
                , "http://www.yahoo.com"
                , "http://www.msn.com"
                , "http://www.apache.org"
                , "http://www.nytimes.com"
                , "http://www.flipkart.com"
                , "http://www.bing.com"
                , "http://www.reddit.com"
                , "http://www.amazon.com"
        };

        UrlFetcher fetcher = new UrlFetcher(urls);
        UrlObject[] contents =  fetcher.goFetch();
        for (UrlObject obj : contents) {
            System.out.println(obj.getContent());
        }
    }
}
