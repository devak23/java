package com.ak.learning.concurrency.throughput;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThroughputServer {
    public static final Path SOURCE_PATH = Paths.get("src/main/resources/war_and_peace.txt");
    public static final Path RESPONSE_PAGE = Paths.get("src/main/resources/template.html");
    public static final int THREAD_POOL_SIZE = 4;
    private static byte[] RESPONSE_BYTES;

    static {
        try {
            RESPONSE_BYTES = Files.readAllBytes(RESPONSE_PAGE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws IOException {
        String text = new String(Files.readAllBytes(SOURCE_PATH));
        startServer(text);
    }

    public static void startServer(String text) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        // Create a context that will assign a handler to a particular HTTP route. In our case we want a WordCountHandler
        // The WordCountHandler will handle each incoming HTTP request and send an HTTP Response
        server.createContext("/search", new WordCountHandler(text));

        // Next we want the incoming http request to be scheduled to a thread from a pool of thread
        Executor executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        server.setExecutor(executor);
        server.start();
        System.out.println("Server is up and running at port 8000");
    }

    // WordCountHandler will handle each incoming request search the word in the text (book) and return the number of
    // times it appears in the book/text
    public static class WordCountHandler implements HttpHandler {
        private String text;

        public WordCountHandler(String text) {
            this.text = text;
        }


        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            // We then get the query part of the URI, break it down into the action and the parameter
            String query = httpExchange.getRequestURI().getQuery();
            String [] keyValue = query.split("=");
            String action = keyValue[0];
            String word = keyValue[1];

            // if the query param is not word, we just return an error
            if (!action.equalsIgnoreCase("word")) {
                httpExchange.sendResponseHeaders(400, -1);
                return;
            }

            // otherwise we just count the number of times the word appears in the book
            long count = countWords(word);

            String response = new String(RESPONSE_BYTES).replace("{1}", word).replace("{2}", String.valueOf(count));
            httpExchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        }

        public long countWords(String word) {
            long count = 0;
            int index = 0;

            while (index >= 0) {
                index = text.indexOf(word, index);
                if (index >= 0) {
                    count++;
                    index++;
                }
            }

            return count;
        }
    }
}
