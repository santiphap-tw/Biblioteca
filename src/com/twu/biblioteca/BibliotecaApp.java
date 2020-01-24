package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.Scanner;

public class BibliotecaApp {

    private ArrayList<Book> books;
    public static final String[] options = {
            "__blank__",
            "Show list of books"
    };

    public BibliotecaApp() {
        books = new ArrayList<Book>();
        addDefaultBooks();
    }

    public void start() {
        showWelcomeMessage();
        showOptions();
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

    public void showOptions() {
        System.out.println("What would you like to do?");
        for(int optionIndex = 1; optionIndex < options.length; optionIndex++){
            System.out.println(optionIndex+ ") " + options[optionIndex]);
        }
        /*System.out.print(">>> ");
        Scanner sc = new Scanner(System.in);
        String option = sc.next();
        selectOption(option);*/
    }

    public void selectOption(String option) {
        if(option.equals("1")){
            showListOfBooks();
            return ;
        }
        System.out.println("Invalid option");
    }

    public void showListOfBooksDetailed() {
        System.out.println("Title\t|\tAuthor\t|\tPublish Date");
        for(Book book : books) {
            System.out.println(book.getTitle() + "\t|\t" + book.getAuthor() + "\t|\t" + book.getPublishDate());
        }
    }

    private void addDefaultBooks() {
        books.add(new Book("Book A", "Santiphap A.", "01/01/2008"));
        books.add(new Book("Book B", "Santiphap B.", "02/01/2008"));
        books.add(new Book("Book C", "Santiphap C.", "03/01/2008"));
    }
}
