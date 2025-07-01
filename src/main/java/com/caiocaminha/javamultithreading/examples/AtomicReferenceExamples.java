package com.caiocaminha.javamultithreading.examples;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceExamples {

    private AtomicReference<String> atomicName;
    private String name;

    public AtomicReferenceExamples(
            String name
    ) {
        this.name = name;
        this.atomicName = new AtomicReference<>(name);
    }

    public void  doSomething(String newName) {
        if(atomicName.compareAndSet(name, newName)){
            this.name = newName;
        } else {
            throw new IllegalArgumentException("");
        }
    }


}
