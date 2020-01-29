package com.twu.biblioteca.model;

public abstract class AppRunnable implements Runnable {

    private String description;

    public AppRunnable(String description) {
        this.description = description;
    }

    public abstract void run(String parameter);

    public String getDescription() {
        return description;
    }
}