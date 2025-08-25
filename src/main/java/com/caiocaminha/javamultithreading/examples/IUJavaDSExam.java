package com.caiocaminha.javamultithreading.examples;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class IUJavaDSExam {


    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>(List.of("cainho", "marizinha"));

        System.out.printf("Initital names: %s", names);

        for(String name : names) {
            if(name.equals("cainho")) {
                names.set(names.indexOf(name), "caio gostoso");
            }
        }

        System.out.printf("Final names: %s", names);
    }

}


