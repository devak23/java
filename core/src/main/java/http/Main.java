package http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        var client = HttpClient.newHttpClient();

        var uri = new URI("http://logout.net");
        var request = HttpRequest.newBuilder(uri).build();

        var handler = HttpResponse.BodyHandlers.ofString();


        /*
         * Synchronously makes the HTTP request and saves its response. This line blocks until the entire request has
         * completed.
         */
        var response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString(
                        Charset.defaultCharset()));
        System.out.println(response.body());
        /*
         * The send method needs a handler to tell it what to do with the response body. Here we use a standard handler
         * to return the body as a String.
         */


        /*
         * This demonstrates the synchronous use of the API. After building our request and client, we issue the HTTP
         * call with the send method. We won’t receive the response object back until the full HTTP call has completed,
         * much like the older HTTP APIs in the JDK.
         * The first parameter is the request we set up, but the second deserves a closer look. Rather than expecting
         * to always return a single type, the send method expects us to provide an implementation of the
         * HttpResponse.BodyHandler<T> interface to tell it how to handle the response. HttpResponse.BodyHandlers
         * provides some useful basic handlers for receiving your response as a byte array, as a string, or as a file.
         * But customizing this behavior is just an implementation of BodyHandler away. All of this plumbing is based
         * on the java.util.concurrent.Flow publisher and subscriber mechanisms, a form of programming known as reactive
         * streams. One of the most significant benefits of HTTP/2 is its built-in multiplexing. Only using a
         * synchronous send doesn’t really gain those benefits, so it should come as no surprise that HttpClient also
         * supports a sendAsync method. sendAsync returns a CompletableFuture wrapped around the HttpResponse, providing
         * a rich set of capabilities that may be familiar from other parts of the platform, as shown here:
         */


        /*
         * Uses CompletableFuture.allOf to wait for all the requests to finish When the future completes, we use
         * thenAccept to receive the response. We can reuse the same client to make multiple requests simultaneously.
         */
        CompletableFuture.allOf(
                client.sendAsync(request, handler)
                        .thenAccept(System.out::println),
                client.sendAsync(request, handler)
                        .thenAccept(System.out::println),
                client.sendAsync(request, handler)
                        .thenAccept(System.out::println),
                client.sendAsync(request, handler)
                        .thenAccept(System.out::println)
        ).join();

        /*
         * Here we set up a request and client again, but then we asynchronously repeat the call three separate times.
         * CompletableFuture.allOf combines these three futures, so we can wait on them all to finish with a single
         * join.
         */


    }
}
