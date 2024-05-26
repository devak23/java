package reflection.privateconstructors;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;

public class WebServer {

    public void startServer() {
        HttpServer httpServer = null;
        try {
            httpServer = HttpServer.create(ServerConfiguration.getInstance().getServerAddress(), 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        httpServer.createContext("/greeting").setHandler(httpExchange -> {
            String responseMessage = ServerConfiguration.getInstance().getGreetingMessage();
            httpExchange.sendResponseHeaders(200, responseMessage.length());
            OutputStream responseBody = httpExchange.getResponseBody();
            responseBody.write(responseMessage.getBytes());
            responseBody.flush();
            responseBody.close();
        });

        System.out.printf("Starting server on address %s:%d",
                ServerConfiguration.getInstance().getServerAddress().getHostName(),
                ServerConfiguration.getInstance().getServerAddress().getPort()
        );

        httpServer.start();
    }
}

/**
 * NOTE: If you forget the forward slash on line 18 (/greeting) and instead have it as just "greeting"... you will get
 * the following error on the console when you run the program:
 * Exception in thread "main" java.lang.IllegalArgumentException: Illegal value for path or protocol
 * 	at jdk.httpserver/sun.net.httpserver.HttpContextImpl.<init>(HttpContextImpl.java:60)
 * 	at jdk.httpserver/sun.net.httpserver.ServerImpl.createContext(ServerImpl.java:282)
 * 	at jdk.httpserver/sun.net.httpserver.HttpServerImpl.createContext(HttpServerImpl.java:78)
 * 	at jdk.httpserver/sun.net.httpserver.HttpServerImpl.createContext(HttpServerImpl.java:39)
 * 	at reflection.privateconstructors.WebServer.startServer(WebServer.java:18)
 * 	at reflection.privateconstructors.Main.main(Main.java:11)
 */