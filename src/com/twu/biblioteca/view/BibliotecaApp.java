package com.twu.biblioteca.view;

import com.twu.biblioteca.controller.Biblioteca;
import com.twu.biblioteca.controller.runnable.OptionRunnable;
import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Label;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class BibliotecaApp {

    private enum STATE {
        INITIAL,
        RUNNING,
        TERMINATE
    }

    private STATE state;
    private Biblioteca biblioteca;
    private Scanner sc = new Scanner(System.in);


    private Map<String, Option> options = new LinkedHashMap<String, Option>() {{
        put(Label.OPTION_SHOW_BOOKS_COMMAND.text, new Option(Label.OPTION_SHOW_BOOKS.text, new OptionRunnable() {
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
        }));
        put(Label.OPTION_CHECKOUT_COMMAND.text, new Option(Label.OPTION_CHECKOUT.text, new OptionRunnable() {
            @Override
            public void run() {
                run("");
            }

            @Override
            public void run(String bookName) {
                boolean isSuccess = biblioteca.doCheckOut(bookName.trim());
                if(isSuccess)
                    System.out.println(Label.CHECKOUT_SUCCESS.text);
                else
                    System.out.println(Label.CHECKOUT_FAIL.text);
            }
        }));
        put(Label.OPTION_RETURN_COMMAND.text, new Option(Label.OPTION_RETURN.text, new OptionRunnable() {
            @Override
            public void run() {
                run("");
            }

            @Override
            public void run(String bookName) {
                boolean isSuccess = biblioteca.doReturn(bookName.trim());
                if(isSuccess)
                    System.out.println(Label.RETURN_SUCCESS.text);
                else
                    System.out.println(Label.RETURN_FAIL.text);
            }
        }));
        put(Label.OPTION_EXIT_COMMAND.text, new Option(Label.OPTION_EXIT.text, new OptionRunnable() {
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

    private final Option invalidOption = new Option(Label.OPTION_INVALID.text, new OptionRunnable() {
        @Override
        public void run(String parameter) {
            run();
        }

        @Override
        public void run() {
            System.out.println(Label.OPTION_INVALID.text);
        }
    });

    public BibliotecaApp() {
        this(new Biblioteca());
    }
    public BibliotecaApp(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        this.state = STATE.INITIAL;
    }

    public void start() {
        while(this.state != BibliotecaApp.STATE.TERMINATE) {
            switch (this.state) {
                case INITIAL:
                    showWelcomeMessage();
                    showOptions();
                    this.state = BibliotecaApp.STATE.RUNNING;
                    break;
                case RUNNING:
                    System.out.print(Label.OPTION_INPUT_PROMPT.text);
                    String option = sc.nextLine();
                    boolean isContinue = selectOption(option);
                    if(!isContinue)
                        this.state = BibliotecaApp.STATE.TERMINATE;
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

    private void showListOfBooks(Biblioteca.BOOK_FILTER bookFilter) {
        System.out.println("Title\t|\tAuthor\t|\tPublish Date");
        int bookNumber = 1;
        for(Book book : biblioteca.getBooks(bookFilter)) {
            System.out.println(bookNumber++ + ") " + book.getTitle() + "\t|\t" + book.getAuthor() + "\t|\t" + book.getPublishDate());
        }
    }

    private class Option {
        String description;
        OptionRunnable operation;

        public Option(String description, OptionRunnable operation) {
            this.description = description;
            this.operation = operation;
        }

        void run(String parameter){
            this.operation.run(parameter);
        }
    }
}
