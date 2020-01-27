package com.twu.biblioteca;

import java.util.*;

public class Biblioteca {

    private enum STATE {
        INITIAL,
        RUNNING,
        TERMINATE
    }

    public enum BOOK_FILTER {
        AVAILABLE,
        NOT_AVAILABLE,
        ALL
    }

    private STATE state;
    private ArrayList<Book> books;
    private Scanner sc = new Scanner(System.in);
    private Map<String, Runnable> options = new LinkedHashMap<String, Runnable>() {{
        put(Label.OPTION_EXIT.text, () -> {
            System.out.println(Label.EXIT.text);
        });
        put(Label.OPTION_SHOW_BOOKS.text, () -> {
            showListOfBooks(BOOK_FILTER.AVAILABLE);
        });
        put(Label.OPTION_CHECKOUT.text, () -> {
            showListOfBooks(BOOK_FILTER.AVAILABLE);
            System.out.println(Label.BOOK_NAME_PROMPT.text);
            sc.nextLine(); // Prevent nextLine skipping
            String bookName = sc.nextLine();
            boolean isSuccess = doCheckOut(bookName.trim());
            if(isSuccess)
                System.out.println(Label.CHECKOUT_SUCCESS.text);
            else
                System.out.println(Label.CHECKOUT_FAIL.text);
        });
        put(Label.OPTION_RETURN.text, () -> {
            showListOfBooks(BOOK_FILTER.NOT_AVAILABLE);
            System.out.println(Label.BOOK_NAME_PROMPT.text);
            sc.nextLine(); // Prevent nextLine skipping
            String bookName = sc.nextLine();
            boolean isSuccess = doReturn(bookName.trim());
            if(isSuccess)
                System.out.println(Label.RETURN_SUCCESS.text);
            else
                System.out.println(Label.RETURN_FAIL.text);
        });
    }};

    public Biblioteca() {
        books = new ArrayList<Book>();
        addDefaultBooks();
        this.state = STATE.INITIAL;
    }

    public ArrayList<Book> getBooks(BOOK_FILTER bookFilter) {
        ArrayList<Book> books = new ArrayList<Book>();
        for(Book book : this.books){
            switch (bookFilter){
                case AVAILABLE:
                    if(book.isAvailable()) books.add(book);
                    break;
                case NOT_AVAILABLE:
                    if(!book.isAvailable()) books.add(book);
                    break;
                case ALL:
                    books.add(book);
                    break;
            }
        }
        return books;
    }

    public boolean doCheckOut(String bookName) {
        for(Book book : this.books) {
            if(bookName.equals(book.getTitle())){
                if(!book.isAvailable()) return false;
                book.doCheckOut();
                return true;
            }
        }
        return false;
    }

    public boolean doReturn(String bookName) {
        for(Book book : this.books) {
            if(bookName.equals(book.getTitle())){
                if(book.isAvailable()) return false;
                book.doReturn();
                return true;
            }
        }
        return false;
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
                    System.out.print(Label.OPTION_INPUT_PROMPT.text);
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

    private void showWelcomeMessage() {
        System.out.println(Label.WELCOME.text);
    }

    private void showOptions() {
        System.out.println(Label.OPTION_PROMPT.text);
        Object[] optionsDescription = options.keySet().toArray();
        for(int optionIndex = 1; optionIndex < optionsDescription.length; optionIndex++){
            System.out.println(optionIndex+ ") " + optionsDescription[optionIndex]);
        }
        System.out.println("0) " + optionsDescription[0]);
    }

    private boolean selectOption(String order) {
        String option = this.getOptionFromOrder(order);
        boolean isExitOption = option.equals(options.keySet().toArray()[0]);
        Runnable operation = options.getOrDefault(option, () -> {
            System.out.println(Label.OPTION_INVALID.text);
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

    private void showListOfBooks(BOOK_FILTER bookFilter) {
        System.out.println("Title\t|\tAuthor\t|\tPublish Date");
        int bookNumber = 1;
        for(Book book : getBooks(bookFilter)) {
            System.out.println(bookNumber++ + ") " + book.getTitle() + "\t|\t" + book.getAuthor() + "\t|\t" + book.getPublishDate());
        }
    }

    private void addDefaultBooks() {
        books.add(new Book("Book A", "Santiphap A.", "01/01/2008"));
        books.add(new Book("Book B", "Santiphap B.", "02/01/2008"));
        books.add(new Book("Book C", "Santiphap C.", "03/01/2008"));
    }
}
