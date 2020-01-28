package com.twu.biblioteca.cli.runnable;

import com.sun.tools.javac.code.Attribute;
import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Movie;
import com.twu.biblioteca.model.Rentable;
import com.twu.biblioteca.model.RunnableWithParameter;

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
        System.out.println("### Books List ###");
        System.out.println("Title\t|\tAuthor\t|\tPublish Date");
        int bookNumber = 1;
        for(Rentable item : biblioteca.getItems(filter)) {
            if(item.getClass() != Book.class) continue;
            Book book = (Book) item;
            System.out.println(bookNumber++ + ") " + book.getTitle() + "\t|\t" + book.getAuthor() + "\t|\t" + book.getPublishDate());
        }
    }

    private void showListOfMovies(Biblioteca.FILTER filter) {
        System.out.println("### Movies List ###");
        System.out.println("Title\t|\tYear\t|\tDirector\t|\tRating");
        int movieNumber = 1;
        for(Rentable item : biblioteca.getItems(filter)) {
            if(item.getClass() != Movie.class) continue;
            Movie movie = (Movie) item;
            System.out.println(movieNumber++ + ") " + movie.getTitle() + "\t|\t" + movie.getYear() + "\t|\t" + movie.getDirector() + "\t|\t" + movie.getRating());
        }
    }
}
