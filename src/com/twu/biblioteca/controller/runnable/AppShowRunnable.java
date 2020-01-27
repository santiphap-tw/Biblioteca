package com.twu.biblioteca.controller.runnable;

import com.twu.biblioteca.controller.Biblioteca;
import com.twu.biblioteca.model.Book;
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
            showListOfBooks(Biblioteca.BOOK_FILTER.NOT_AVAILABLE);
        else if(parameter.equals("all"))
            showListOfBooks(Biblioteca.BOOK_FILTER.ALL);
        else
            showListOfBooks(Biblioteca.BOOK_FILTER.AVAILABLE);
    }

    private void showListOfBooks(Biblioteca.BOOK_FILTER bookFilter) {
        System.out.println("Title\t|\tAuthor\t|\tPublish Date");
        int bookNumber = 1;
        for(Book book : biblioteca.getBooks(bookFilter)) {
            System.out.println(bookNumber++ + ") " + book.getTitle() + "\t|\t" + book.getAuthor() + "\t|\t" + book.getPublishDate());
        }
    }
}
