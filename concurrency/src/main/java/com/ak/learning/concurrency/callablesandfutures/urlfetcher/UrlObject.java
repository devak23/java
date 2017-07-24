package com.ak.learning.concurrency.callablesandfutures.urlfetcher;

public class UrlObject {
    private String url;
    private String content;

    public UrlObject(String url, String content) {
        this.url = url;
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public UrlObject setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getContent() {
        return content;
    }

    public UrlObject setContent(String content) {
        this.content = content;
        return this;
    }
}
