package com.ak.learning.concurrency.callablesandfutures.urlfetcher;


import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Callable;

public class ContentFetcher implements Callable<UrlObject> {
    private String url;

    public ContentFetcher(String url) {
        this.url = url;
    }

    @Override
    public UrlObject call() throws Exception {
        InputStream in = new URL(url).openStream();
        String content = IOUtils.toString(in, "UTF-8");
        return new UrlObject(url, content);
    }
}
