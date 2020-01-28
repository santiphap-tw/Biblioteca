package com.twu.biblioteca.cli;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.runnable.*;
import com.twu.biblioteca.model.*;

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
    private Map<String, AppOption> options;
    private Runnable initialTasks;
    private final AppOption invalidOption = new AppOption(Label.OPTION_INVALID.text, new AppInvalidRunnable());

    public BibliotecaApp() {
        this(new Biblioteca());
    }
    public BibliotecaApp(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        this.state = STATE.INITIAL;
        // Initialise option behaviors
        options = new LinkedHashMap<String, AppOption>();
        options.put(Label.OPTION_SHOW_ALL_COMMAND.text, new AppOption(Label.OPTION_SHOW_ALL.text, new AppShowRunnable(biblioteca, Rentable.class)));
        options.put(Label.OPTION_SHOW_BOOKS_COMMAND.text, new AppOption(Label.OPTION_SHOW_BOOKS.text, new AppShowRunnable(biblioteca, Book.class)));
        options.put(Label.OPTION_SHOW_MOVIES_COMMAND.text, new AppOption(Label.OPTION_SHOW_MOVIES.text, new AppShowRunnable(biblioteca, Movie.class)));
        options.put(Label.OPTION_CHECKOUT_COMMAND.text, new AppOption(Label.OPTION_CHECKOUT.text, new AppCheckOutRunnable(biblioteca)));
        options.put(Label.OPTION_RETURN_COMMAND.text, new AppOption(Label.OPTION_RETURN.text, new AppReturnRunnable(biblioteca)));
        options.put(Label.OPTION_EXIT_COMMAND.text, new AppOption(Label.OPTION_EXIT.text, new AppExitRunnable()));
        // Initialise initial tasks
        initialTasks = new AppInitialRunnable(options);
    }

    public void start() {
        while(this.state != BibliotecaApp.STATE.TERMINATE) {
            switch (this.state) {
                case INITIAL:
                    initialTasks.run();
                    this.state = BibliotecaApp.STATE.RUNNING;
                    break;
                case RUNNING:
                    System.out.print(Label.OPTION_INPUT_PROMPT.text);
                    String option = sc.nextLine();
                    boolean isExitOption = selectOption(option);
                    if(isExitOption)
                        this.state = BibliotecaApp.STATE.TERMINATE;
                    break;
                case TERMINATE:
                    break;
            }
        }
    }

    private boolean selectOption(String command) {
        String[] commandSplit = command.split(" ", 2);
        String option = commandSplit[0];
        String parameter = commandSplit.length > 1 ? commandSplit[1] : "";
        AppOption selectedOption = options.getOrDefault(option, invalidOption);
        selectedOption.run(parameter);
        boolean isExitOption = selectedOption.getDescription().equals(Label.OPTION_EXIT.text);
        return isExitOption;
    }
}
