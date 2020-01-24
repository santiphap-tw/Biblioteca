package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.Scanner;

public class BibliotecaApp {

    private ArrayList<Book> books;
    private Scanner sc = new Scanner(System.in);

    public static final String[] options = {
            "Exit",
            "Show list of books",
            "Check out",
            "Return"
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
        System.out.println("Title\t|\tAuthor\t|\tPublish Date");
        int bookNumber = 1;
        for(Book book : getBooks()) {
            System.out.println(bookNumber++ + ") " + book.getTitle() + "\t|\t" + book.getAuthor() + "\t|\t" + book.getPublishDate());
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
            showListOfBooks();
            System.out.println("Enter the book name:");
            sc.nextLine(); // Prevent nextLine skipping
            String bookName = sc.nextLine();
            boolean isSuccess = checkOut(bookName.trim());
            if(isSuccess)
                System.out.println("Thank you! Enjoy the book");
            else
                System.out.println("Sorry, that book is not available");
            return true;
        }
        if(option.equals("3")){
            showListOfBooks();
            System.out.println("Enter the book name:");
            sc.nextLine(); // Prevent nextLine skipping
            String bookName = sc.nextLine();
            boolean isSuccess = checkIn(bookName.trim());
            if(isSuccess)
                System.out.println("Thank you for returning the book");
            else
                System.out.println("This is not a valid book to return");
            return true;
        }
        if(option.equals("0")){
            System.out.println("Thank you for using Biblioteca!");
            return false;
        }
        System.out.println("Please select a valid option!");
        return true;
    }

    private void addDefaultBooks() {
        books.add(new Book("Book A", "Santiphap A.", "01/01/2008"));
        books.add(new Book("Book B", "Santiphap B.", "02/01/2008"));
        books.add(new Book("Book C", "Santiphap C.", "03/01/2008"));
    }

    public ArrayList<Book> getBooks() {
        ArrayList<Book> availableBooks = new ArrayList<Book>();
        for(Book book : books){
            if(book.isAvailable())availableBooks.add(book);
        }
        return availableBooks;
    }

    private boolean checkOut(int bookNumber) {
        if(bookNumber > 0 && bookNumber <= books.size()){
            Book book = books.get(bookNumber-1);
            if(!book.isAvailable()) return false;
            book.checkOut();
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkOut(String bookName) {
        int bookNumber = getBookNumber(bookName);
        return checkOut(bookNumber);
    }

    private int getBookNumber(String bookName) {
        for(int bookNumber=0; bookNumber<books.size(); bookNumber++){
            Book book = books.get(bookNumber);
            if(book.getTitle().equals(bookName)){
                return bookNumber+1;
            }
        }
        return -1;
    }

    public boolean checkIn(String bookName) {
        int bookNumber = getBookNumber(bookName);
        return checkIn(bookNumber);
    }

    private boolean checkIn(int bookNumber) {
        if(bookNumber > 0 && bookNumber <= books.size()){
            Book book = books.get(bookNumber-1);
            if(book.isAvailable()) return false;
            book.checkIn();
            return true;
        }
        else {
            return false;
        }
    }
}
