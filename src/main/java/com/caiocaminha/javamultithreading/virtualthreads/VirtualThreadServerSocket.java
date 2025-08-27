package com.caiocaminha.javamultithreading.virtualthreads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class VirtualThreadServerSocket {
    public static void main(String[] args) throws IOException {
        int portNumber = 8082;
        try(
                ServerSocket serverSocket = new ServerSocket(portNumber)
                ){
            while(true){
                Socket clientSocket = serverSocket.accept();
                Thread.ofVirtual().start(() -> {
                    try(
                            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            ) {
                        String inputLine;
                        while( (inputLine = in.readLine()) != null) {
                            System.out.println(inputLine);
                            out.println(inputLine);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
