package com.caiocaminha.javamultithreading.examples;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class ThreadPerCoreNonBlockingExample {
    private HttpClient httpClient;


    private void startHttpServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        HttpContext context = server.createContext("/");

        /**
         * This works because
         * Method reference in Java can be used to create Instances of Functional Interface
         *
         * This is creating an instance of HttpHandler functional interface which only has one method that returns
         * void and received an HttpExchange object
         */
        context.setHandler(this::handleHttpRequest);
    }

    private void handleHttpRequest(HttpExchange httpExchange) throws IOException {
        int numberOfProducts = parseRequest(httpExchange);
        URI requestUri = URI.create(
                String.format(
                        "best-online-store/products?number-of-products=%d",
                        numberOfProducts
                )
        );

        CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(
                HttpRequest.newBuilder()
                        .GET()
                        .uri(requestUri)
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        );

        responseFuture.thenAccept(response -> {
           System.out.println(response.statusCode());
        });
    }

    private int parseRequest(HttpExchange httpExchange) throws IOException {
        return httpExchange.getRequestBody().read();
    }

}
