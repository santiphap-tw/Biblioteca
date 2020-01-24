package com.twu.biblioteca;

import java.util.ArrayList;

public class BibliotecaApp {

    private ArrayList<Book> books;

    public BibliotecaApp() {
        books = new ArrayList<Book>();
        addDefaultBooks();
        showWelcomeMessage();
        showListOfBooks();
    }

    public void showWelcomeMessage() {
        System.out.println("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!");
    }

    public void showListOfBooks() {
        System.out.println("List of Books:");
        for(Book book : books) {
            System.out.println("- " + book.getTitle());
        }
    }

    private void addDefaultBooks() {
        books.add(new Book("Book A", "Santiphap A.", "01/01/2008"));
        books.add(new Book("Book B", "Santiphap B.", "02/01/2008"));
        books.add(new Book("Book C", "Santiphap C.", "03/01/2008"));
    }
}
