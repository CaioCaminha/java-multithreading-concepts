package com.caiocaminha.javamultithreading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.SimpleThreadScope;

@SpringBootApplication
public class JavaMultithreadingApplication {



    public static void main(String[] args) {
        //Thread.interrupted();

        var newThread = new Thread();
        newThread.start();
        SpringApplication.run(JavaMultithreadingApplication.class, args);
    }

}
