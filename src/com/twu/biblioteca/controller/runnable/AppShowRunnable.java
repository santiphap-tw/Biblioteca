package com.twu.biblioteca.controller.runnable;

import com.twu.biblioteca.controller.Biblioteca;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.view.BibliotecaApp;

public class AppShowRunnable implements RunnableWithParameter{

    private BibliotecaApp app;

    public AppShowRunnable(BibliotecaApp app) {
        this.app = app;
    }

    @Override
    public void run() {
        run("available");
    }

    @Override
    public void run(String parameter) {
        if(parameter.equals("not available"))
            app.showListOfBooks(Biblioteca.BOOK_FILTER.NOT_AVAILABLE);
        else if(parameter.equals("all"))
            app.showListOfBooks(Biblioteca.BOOK_FILTER.ALL);
        else
            app.showListOfBooks(Biblioteca.BOOK_FILTER.AVAILABLE);
    }
}
