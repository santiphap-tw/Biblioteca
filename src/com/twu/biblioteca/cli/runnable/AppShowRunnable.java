package com.twu.biblioteca.cli.runnable;

import com.sun.tools.javac.code.Attribute;
import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.BibliotecaApp;
import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Movie;
import com.twu.biblioteca.model.Rentable;
import com.twu.biblioteca.model.RunnableWithParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppShowRunnable implements RunnableWithParameter {

    private Biblioteca biblioteca;
    private Class targetClass;
    private Map<String, Biblioteca.FILTER> stringToFilter = new HashMap<String, Biblioteca.FILTER>() {{
       put("available", Biblioteca.FILTER.AVAILABLE);
       put("not available", Biblioteca.FILTER.NOT_AVAILABLE);
       put("all", Biblioteca.FILTER.ALL);
    }};

    public AppShowRunnable(Biblioteca biblioteca) {
        this(biblioteca, Rentable.class);
    }
    public AppShowRunnable(Biblioteca biblioteca, Class targetClass) {
        this.targetClass = targetClass;
        this.biblioteca = biblioteca;
    }

    @Override
    public void run() {
        run("available");
    }

    @Override
    public void run(String parameter) {
        Biblioteca.FILTER filter = stringToFilter.getOrDefault(parameter, Biblioteca.FILTER.AVAILABLE);
        if(targetClass == Book.class || targetClass == Rentable.class)
            showListOfBooks(filter);
        if(targetClass == Movie.class || targetClass == Rentable.class)
            showListOfMovies(filter);
    }

    private void showListOfBooks(Biblioteca.FILTER filter) {
        ArrayList<Book> books = new ArrayList<Book>();
        for(Rentable item : biblioteca.getItems(filter)) {
            if(item.getClass() == Book.class) books.add((Book) item);
        }
        boolean showBorrower = filter != Biblioteca.FILTER.AVAILABLE;
        BibliotecaApp.printBooks(books, showBorrower);
    }

    private void showListOfMovies(Biblioteca.FILTER filter) {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        for(Rentable item : biblioteca.getItems(filter)) {
            if(item.getClass() == Movie.class) movies.add((Movie) item);
        }
        boolean showBorrower = filter != Biblioteca.FILTER.AVAILABLE;
        BibliotecaApp.printMovie(movies, showBorrower);
    }
}
