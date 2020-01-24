package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.Scanner;

public class BibliotecaApp {

    private ArrayList<Book> books;
    private Scanner sc = new Scanner(System.in);

    public static final String[] options = {
            "Exit",
            "Show list of books",
            "Check out"
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
        int bookNumber = 1;
        for(Book book : books) {
            System.out.println(bookNumber++ + ") " + book.getTitle());
        }
    }

    public void showOptions() {
        System.out.println("What would you like to do?");
        for(int optionIndex = 1; optionIndex < options.length; optionIndex++){
            System.out.println(optionIndex+ ") " + options[optionIndex]);
        }
        System.out.println("0) " + options[0]);
        startInput();
    }

    private void startInput() {
        System.out.print(">>> ");
        String option = sc.next();
        boolean continueInput = selectOption(option);
        if(continueInput) startInput();
    }

    public boolean selectOption(String option) {
        if(option.equals("1")){
            showListOfBooks();
            return true;
        }
        if(option.equals("2")){
            showListOfBooksDetailed();
            System.out.print("Enter the book number:");
            int bookNumber = sc.nextInt();
            checkOut(bookNumber);
            return true;
        }
        if(option.equals("0")){
            System.out.println("Thank you for using Biblioteca!");
            return false;
        }
        System.out.println("Please select a valid option!");
        return true;
    }

    public void showListOfBooksDetailed() {
        System.out.println("Title\t|\tAuthor\t|\tPublish Date");
        int bookNumber = 1;
        for(Book book : books) {
            System.out.println(bookNumber++ + ") " + book.getTitle() + "\t|\t" + book.getAuthor() + "\t|\t" + book.getPublishDate());
        }
    }

    private void addDefaultBooks() {
        books.add(new Book("Book A", "Santiphap A.", "01/01/2008"));
        books.add(new Book("Book B", "Santiphap B.", "02/01/2008"));
        books.add(new Book("Book C", "Santiphap C.", "03/01/2008"));
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void checkOut(int bookNumber) {
        if(bookNumber > 0 && bookNumber <= books.size()){
            books.remove(bookNumber-1);
            System.out.println("Thank you! Enjoy the book");
        }
        else {
            System.out.println("Invalid book number. Please try again.");
        }
    }
}
