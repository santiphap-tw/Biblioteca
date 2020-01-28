package com.twu.biblioteca.cli.runnable;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Rentable;
import com.twu.biblioteca.model.RunnableWithParameter;

public class AppShowRunnable implements RunnableWithParameter {

    private Biblioteca biblioteca;

    public AppShowRunnable(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    @Override
    public void run() {
        run("available");
    }

    @Override
    public void run(String parameter) {
        if(parameter.equals("not available"))
            showListOfBooks(Biblioteca.FILTER.NOT_AVAILABLE);
        else if(parameter.equals("all"))
            showListOfBooks(Biblioteca.FILTER.ALL);
        else
            showListOfBooks(Biblioteca.FILTER.AVAILABLE);
    }

    private void showListOfBooks(Biblioteca.FILTER filter) {
        System.out.println("Title\t|\tAuthor\t|\tPublish Date");
        int bookNumber = 1;
        for(Rentable item : biblioteca.getItems(filter)) {
            if(item.getClass() != Book.class) continue;
            Book book = (Book) item;
            System.out.println(bookNumber++ + ") " + book.getTitle() + "\t|\t" + book.getAuthor() + "\t|\t" + book.getPublishDate());
        }
    }
}
