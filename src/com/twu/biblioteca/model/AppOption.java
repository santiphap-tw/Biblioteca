package com.twu.biblioteca.model;

public class AppOption {
    private String description;
    private RunnableWithParameter operation;

    public AppOption(String description, RunnableWithParameter operation) {
        this.description = description;
        this.operation = operation;
    }

    public void run(String parameter){
        this.operation.run(parameter);
    }

    public String getDescription() {
        return description;
    }
}