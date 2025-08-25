package com.caiocaminha.javamultithreading.udpExamples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UDPExample {
    private List<String> quotes = new ArrayList<>();
    private DatagramSocket socket;

    public static void main(String[] args) {
        String hostname = "djxmmx.net";
        int port = 17;

        try {
            DatagramSocket socket = new DatagramSocket(port);
            byte[] buffer = new byte[256];

            DatagramPacket request = new DatagramPacket(buffer, buffer.length);

            InetAddress address = request.getAddress();
            int clientPort = request.getPort();
            String data = "Message from server";
            buffer = data.getBytes();

            DatagramPacket response = new DatagramPacket(
                    buffer,
                    buffer.length,
                    address,
                    clientPort
            );

            var requestThread = Thread.ofVirtual().start(
                    () -> {
                        try{
                            socket.send(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );

            var responseThread = Thread.ofVirtual().start(
                    () -> {
                        try{
                            socket.receive(request);
                            System.out.println(new String(request.getData(), StandardCharsets.UTF_8));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );

            requestThread.join();
            responseThread.join();



        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    private void service() throws IOException {
        while (true) {
            DatagramPacket request = new DatagramPacket(new byte[1], 1);
            socket.receive(request);

            String quote = getRandomQuote();
            byte[] buffer = quote.getBytes(StandardCharsets.UTF_8);

            InetAddress address = request.getAddress();
            int port = request.getPort();

            DatagramPacket response = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(response);
        }
    }


    private void loadQuoteFromFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String aquote;

        while((aquote = reader.readLine()) != null) {
            quotes.add(aquote);
        }
        reader.close();
    }

    private String getRandomQuote() {
        return quotes.get(new Random().nextInt());
    }

}
