package com.twu.biblioteca.model;

import java.util.ArrayList;

public abstract class AppOperation{

    protected String description;

    public AppOperation(String description) {
        this.description = description;
    }

    public abstract ArrayList<String> run(String parameter);

    public String getDescription() {
        return description;
    }
}