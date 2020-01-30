package com.twu.biblioteca.model;

public abstract class AppOperation implements Runnable {

    private String description;

    public AppOperation(String description) {
        this.description = description;
    }

    public abstract void run(String parameter);

    public String getDescription() {
        return description;
    }
}