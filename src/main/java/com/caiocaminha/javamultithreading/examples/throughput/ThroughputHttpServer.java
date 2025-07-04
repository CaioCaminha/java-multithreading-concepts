package com.caiocaminha.javamultithreading.examples.throughput;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThroughputHttpServer {
    private static final int NUMBER_OF_THREADS = 1;
    public static void main(String[] args) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get("")));
        startServer(text);
    }

    public static void startServer(String text) throws IOException {
        HttpServer server = HttpServer.create(
                new InetSocketAddress(8080), 0
        );

        server.createContext("/search", new WordCounterHandler(text)); //passing a custom HttpHandler
        /***
         * Creating an instance of ThreadPoolExecutor which extends Executor
         * Then setting the Executor for HttpServer before starting the server
         * This way the requests will be handled in tasks that are processed by the Worker threads
         */
        Executor executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        server.setExecutor(executor);
        server.start();
    }

    private static class WordCounterHandler implements HttpHandler {
        private String text;
        public WordCounterHandler(String text) {
            this.text = text;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery();
            String[] keyValue = query.split("=");
            String action = keyValue[0];
            String word = keyValue[1];

            if(!action.equals("word")){
                exchange.sendResponseHeaders(400, 0);
                return;
            }

            long count = countWord(word);

            byte[] response = Long.toString(count).getBytes();
            exchange.sendResponseHeaders(200, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        }

        private long countWord(String word) {
            long count = 0;
            int index = 0;

            while(index >= 0) {
                index = word.indexOf(word, index);
                if(index >= 0) {
                    count++;
                    index++;
                }
            }
            return count;
        }
    }
}
