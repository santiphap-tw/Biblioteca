package com.twu.biblioteca.model;

import com.twu.biblioteca.cli.BibliotecaApp;

import java.util.ArrayList;

public abstract class AppOperation{

    protected String description;
    protected BibliotecaApp.RESPONSE response;

    public AppOperation(String description) {
        this.description = description;
    }

    public abstract ArrayList<String> run(String parameter);

    public String getDescription() {
        return description;
    }

    public BibliotecaApp.RESPONSE getResponse() {
        return response;
    }
}