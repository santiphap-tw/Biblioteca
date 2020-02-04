package com.twu.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    public static Biblioteca biblioteca;

    public static void main(String[] args) {
        biblioteca = new Biblioteca();
        SpringApplication.run(App.class, args);
    }
}
