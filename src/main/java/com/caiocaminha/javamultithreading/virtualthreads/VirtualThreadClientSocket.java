package com.caiocaminha.javamultithreading.virtualthreads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class VirtualThreadClientSocket {
    public static void main(String[] args) {
        String hostName = "localhost";
        int port = 8082;
        try(
                Socket echoSocket = new Socket(hostName, port);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                ) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
