package com.twu.biblioteca.cli;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.operation.*;
import com.twu.biblioteca.model.*;

import java.util.ArrayList;
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
    private Map<String, AppOperation> options;
    private AppOperation initialTasks;
    private final AppOperation invalidOption = new InvalidOperation(Label.OPTION_INVALID.text);
    private Scanner sc = new Scanner(System.in);

    public BibliotecaApp() {
        Biblioteca.getInstance().initialize();
        this.state = STATE.INITIAL;
        // Initialize options
        options = new LinkedHashMap<String, AppOperation>();
        options.put(Label.OPTION_SHOW_ALL_COMMAND.text, new ShowOperation(Label.OPTION_SHOW_ALL.text, Rental.class));
        options.put(Label.OPTION_SHOW_BOOKS_COMMAND.text, new ShowOperation(Label.OPTION_SHOW_BOOKS.text, Book.class));
        options.put(Label.OPTION_SHOW_MOVIES_COMMAND.text, new ShowOperation(Label.OPTION_SHOW_MOVIES.text, Movie.class));
        options.put(Label.OPTION_LOGIN_COMMAND.text, new LoginOperation(Label.OPTION_LOGIN.text));
        options.put(Label.OPTION_LOGOUT_COMMAND.text, new LogoutOperation(Label.OPTION_LOGOUT.text));
        options.put(Label.OPTION_CHECKOUT_COMMAND.text, new CheckOutOperation(Label.OPTION_CHECKOUT.text));
        options.put(Label.OPTION_RETURN_COMMAND.text, new ReturnOperation(Label.OPTION_RETURN.text));
        options.put(Label.OPTION_MY_INFO_COMMAND.text, new MyInfoOperation(Label.OPTION_MY_INFO.text));
        options.put(Label.OPTION_MY_BORROWING_COMMAND.text, new MyBorrowOperation(Label.OPTION_MY_BORROWING.text));
        options.put(Label.OPTION_HELP_COMMAND.text, new StartOperation(Label.OPTION_HELP.text, options));
        options.put(Label.OPTION_EXIT_COMMAND.text, new ExitOperation(Label.OPTION_EXIT.text));
        // Initialize initial tasks
        initialTasks = new StartOperation("", options);
    }

    public static void printOutput(ArrayList<String> output){
        output.forEach(System.out::println);
    }

    public void start() {
        while(this.state != BibliotecaApp.STATE.TERMINATE) {
            switch (this.state) {
                case INITIAL:
                    printOutput(initialTasks.run(""));
                    this.state = BibliotecaApp.STATE.RUNNING;
                    break;
                case RUNNING:
                    System.out.print(Label.OPTION_INPUT_PROMPT.text);
                    String option = sc.nextLine();
                    runCommand(option).stream()
                        .filter(out -> out.equals(Label.EXIT.text))
                        .findFirst()
                        .ifPresent(out -> this.state = BibliotecaApp.STATE.TERMINATE);
                    break;
                case TERMINATE:
                    break;
            }
        }
    }

    public ArrayList<String> runCommand(String command) {
        // Split command into option & parameter
        String[] commandSplit = command.split(" ", 2);
        String option = commandSplit[0];
        String parameter = commandSplit.length > 1 ? commandSplit[1] : "";
        return runOption(option,parameter);
    }

    private ArrayList<String> runOption(String option, String parameter){
        AppOperation selectedOption = options.getOrDefault(option, invalidOption);
        ArrayList<String> output = selectedOption.run(parameter);
        BibliotecaApp.printOutput(output);
        return output;
    }
}
