package com.twu.biblioteca;

import java.util.*;

public class Biblioteca {

    enum STATE {
        INITIAL,
        RUNNING,
        TERMINATE
    }

    private STATE state;
    private ArrayList<Book> books;
    private Scanner sc = new Scanner(System.in);
    private Map<String, Runnable> options = new LinkedHashMap<String, Runnable>() {{
        put("Exit", () -> {
            System.out.println("Thank you for using Biblioteca!");
        });
        put("Show list of books", () -> {
            showListOfBooks();
        });
        put("Check out", () -> {
            showListOfBooks();
            System.out.println("Enter the book name:");
            sc.nextLine(); // Prevent nextLine skipping
            String bookName = sc.nextLine();
            boolean isSuccess = checkOut(bookName.trim());
            if(isSuccess)
                System.out.println("Thank you! Enjoy the book");
            else
                System.out.println("Sorry, that book is not available");
        });
        put("Return", () -> {
            showListOfBooks(true);
            System.out.println("Enter the book name:");
            sc.nextLine(); // Prevent nextLine skipping
            String bookName = sc.nextLine();
            boolean isSuccess = checkIn(bookName.trim());
            if(isSuccess)
                System.out.println("Thank you for returning the book");
            else
                System.out.println("This is not a valid book to return");
        });
    }};

    public Biblioteca() {
        books = new ArrayList<Book>();
        addDefaultBooks();
        this.state = STATE.INITIAL;
    }

    public void start() {
        while(this.state != STATE.TERMINATE) {
            switch (this.state) {
                case INITIAL:
                    showWelcomeMessage();
                    showOptions();
                    this.state = STATE.RUNNING;
                    break;
                case RUNNING:
                    System.out.print(">>> ");
                    String option = sc.next();
                    boolean isContinue = selectOption(option);
                    if(!isContinue)
                        this.state = STATE.TERMINATE;
                    break;
                case TERMINATE:
                    break;
            }
        }
    }

    // STATE - INITIAL
    public void showWelcomeMessage() {
        System.out.println("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!");
    }

    // STATE - INITIAL
    public void showOptions() {
        System.out.println("What would you like to do?");
        Object[] optionsDescription = options.keySet().toArray();
        for(int optionIndex = 1; optionIndex < optionsDescription.length; optionIndex++){
            System.out.println(optionIndex+ ") " + optionsDescription[optionIndex]);
        }
        System.out.println("0) " + optionsDescription[0]);
    }

    // STATE - RUNNING
    public boolean selectOption(String order) {
        String option = this.getOptionFromOrder(order);
        boolean isExitOption = option.equals(options.keySet().toArray()[0]);
        Runnable operation = options.getOrDefault(option, () -> {
            System.out.println("Please select a valid option!");
        });
        operation.run();
        return !isExitOption;
    }

    private String getOptionFromOrder(String order) {
        Object[] optionsDescription = options.keySet().toArray();
        String option = "";
        try{
            option = (String) optionsDescription[Integer.parseInt(order)];
        } catch (NumberFormatException e) {
            option = "Invalid";
        }
        return option;
    }

    public void showListOfBooks() {
        showListOfBooks(false);
    }

    public void showListOfBooks(boolean onlyNA) {
        System.out.println("Title\t|\tAuthor\t|\tPublish Date");
        int bookNumber = 1;
        for(Book book : getBooks(onlyNA)) {
            System.out.println(bookNumber++ + ") " + book.getTitle() + "\t|\t" + book.getAuthor() + "\t|\t" + book.getPublishDate());
        }
    }

    public ArrayList<Book> getBooks() {
        return getBooks(false);
    }

    public ArrayList<Book> getBooks(boolean onlyNA) {
        ArrayList<Book> books = new ArrayList<Book>();
        for(Book book : this.books){
            if(onlyNA && !book.isAvailable()) books.add(book);
            if(!onlyNA && book.isAvailable()) books.add(book);
        }
        return books;
    }

    public boolean checkOut(String bookName) {
        int bookNumber = getBookNumber(bookName);
        return checkOut(bookNumber);
    }

    public boolean checkIn(String bookName) {
        int bookNumber = getBookNumber(bookName);
        return checkIn(bookNumber);
    }

    private void addDefaultBooks() {
        books.add(new Book("Book A", "Santiphap A.", "01/01/2008"));
        books.add(new Book("Book B", "Santiphap B.", "02/01/2008"));
        books.add(new Book("Book C", "Santiphap C.", "03/01/2008"));
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

    private int getBookNumber(String bookName) {
        for(int bookNumber=0; bookNumber<books.size(); bookNumber++){
            Book book = books.get(bookNumber);
            if(book.getTitle().equals(bookName)){
                return bookNumber+1;
            }
        }
        return -1;
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
