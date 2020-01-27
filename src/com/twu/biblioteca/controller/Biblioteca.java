package com.twu.biblioteca.controller;

import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Label;

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
    private Map<String, Option> options = new LinkedHashMap<String, Option>() {{
        put(Label.OPTION_SHOW_BOOKS_COMMAND.text, new Option(Label.OPTION_SHOW_BOOKS.text, new RunnableWithParameter() {
            @Override
            public void run() {
                run("available");
            }

            @Override
            public void run(String parameter) {
                if(parameter.equals("not available"))
                    showListOfBooks(BOOK_FILTER.NOT_AVAILABLE);
                else if(parameter.equals("all"))
                    showListOfBooks(BOOK_FILTER.ALL);
                else
                    showListOfBooks(BOOK_FILTER.AVAILABLE);
            }
        }));
        put(Label.OPTION_CHECKOUT_COMMAND.text, new Option(Label.OPTION_CHECKOUT.text, new RunnableWithParameter() {
            @Override
            public void run() {
                run("");
            }

            @Override
            public void run(String bookName) {
                boolean isSuccess = doCheckOut(bookName.trim());
                if(isSuccess)
                    System.out.println(Label.CHECKOUT_SUCCESS.text);
                else
                    System.out.println(Label.CHECKOUT_FAIL.text);
            }
        }));
        put(Label.OPTION_RETURN_COMMAND.text, new Option(Label.OPTION_RETURN.text, new RunnableWithParameter() {
            @Override
            public void run() {
                run("");
            }

            @Override
            public void run(String bookName) {
                boolean isSuccess = doReturn(bookName.trim());
                if(isSuccess)
                    System.out.println(Label.RETURN_SUCCESS.text);
                else
                    System.out.println(Label.RETURN_FAIL.text);
            }
        }));
        put(Label.OPTION_EXIT_COMMAND.text, new Option(Label.OPTION_EXIT.text, new RunnableWithParameter() {
            @Override
            public void run() {
                System.out.println(Label.EXIT.text);
            }

            @Override
            public void run(String parameter) {
                run();
            }
        }));
    }};
    private final Option invalidOption = new Option(Label.OPTION_INVALID.text, new RunnableWithParameter() {
        @Override
        public void run(String parameter) {
            run();
        }

        @Override
        public void run() {
            System.out.println(Label.OPTION_INVALID.text);
        }
    });

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
                    System.out.print(Label.OPTION_INPUT_PROMPT.text);
                    String option = sc.nextLine();
                    boolean isContinue = selectOption(option);
                    if(!isContinue)
                        this.state = STATE.TERMINATE;
                    break;
                case TERMINATE:
                    break;
            }
        }
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

    private void showWelcomeMessage() {
        System.out.println(Label.WELCOME.text);
    }

    private void showOptions() {
        System.out.println(Label.OPTION_PROMPT.text);
        options.forEach((command, option) -> {
            System.out.println(command+ "\t\t" + option.description);
        });
    }

    private boolean selectOption(String option) {
        String[] optionArray = option.split(" ", 2);
        option = optionArray[0];
        String parameter = optionArray.length > 1 ? optionArray[1] : "";
        Option selectedOption = options.getOrDefault(option, invalidOption);
        selectedOption.run(parameter);
        boolean isExitOption = selectedOption.description.equals(Label.OPTION_EXIT.text);
        return !isExitOption;
    }

    private void showListOfBooks(BOOK_FILTER bookFilter) {
        System.out.println("Title\t|\tAuthor\t|\tPublish Date");
        int bookNumber = 1;
        for(Book book : getBooks(bookFilter)) {
            System.out.println(bookNumber++ + ") " + book.getTitle() + "\t|\t" + book.getAuthor() + "\t|\t" + book.getPublishDate());
        }
    }

    private void addDefaultBooks(){
        books.add(new Book("Book A", "Santiphap A.", "01/01/2008"));
        books.add(new Book("Book B", "Santiphap B.", "02/01/2008"));
        books.add(new Book("Book C", "Santiphap C.", "03/01/2008"));
    }

    private class Option {
        String description;
        RunnableWithParameter operation;

        public Option(String description, RunnableWithParameter operation) {
            this.description = description;
            this.operation = operation;
        }

        void run(String parameter){
            this.operation.run(parameter);
        }
    }

    private abstract class RunnableWithParameter implements Runnable {
        public abstract void run(String parameter);
    }
}
