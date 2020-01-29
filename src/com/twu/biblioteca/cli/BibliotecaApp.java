package com.twu.biblioteca.cli;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.runnable.*;
import com.twu.biblioteca.model.*;

import java.util.*;

public class BibliotecaApp {

    private enum STATE {
        INITIAL,
        RUNNING,
        TERMINATE
    }

    public enum RESPONSE {
        VALID,
        INVALID,
        EXIT
    }

    private STATE state;
    private Biblioteca biblioteca;
    private Scanner sc = new Scanner(System.in);
    private Map<String, AppRunnable> options;
    private Runnable initialTasks;
    private final AppRunnable invalidOption = new AppInvalidRunnable(Label.OPTION_INVALID.text);

    public BibliotecaApp() {
        this(new Biblioteca());
    }
    public BibliotecaApp(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        this.state = STATE.INITIAL;
        // Initialise option behaviors
        options = new LinkedHashMap<String, AppRunnable>();
        options.put(Label.OPTION_SHOW_ALL_COMMAND.text, new AppShowRunnable(Label.OPTION_SHOW_ALL.text, biblioteca, Rental.class));
        options.put(Label.OPTION_SHOW_BOOKS_COMMAND.text, new AppShowRunnable(Label.OPTION_SHOW_BOOKS.text, biblioteca, Book.class));
        options.put(Label.OPTION_SHOW_MOVIES_COMMAND.text, new AppShowRunnable(Label.OPTION_SHOW_MOVIES.text, biblioteca, Movie.class));
        options.put(Label.OPTION_LOGIN_COMMAND.text, new AppLoginRunnable(Label.OPTION_LOGIN.text, biblioteca));
        options.put(Label.OPTION_LOGOUT_COMMAND.text, new AppLogoutRunnable(Label.OPTION_LOGOUT.text, biblioteca));
        options.put(Label.OPTION_CHECKOUT_COMMAND.text, new AppCheckOutRunnable(Label.OPTION_CHECKOUT.text, biblioteca));
        options.put(Label.OPTION_RETURN_COMMAND.text, new AppReturnRunnable(Label.OPTION_RETURN.text, biblioteca));
        options.put(Label.OPTION_MY_INFO_COMMAND.text, new AppMyInfoRunnable(Label.OPTION_MY_INFO.text, biblioteca));
        options.put(Label.OPTION_MY_BORROWING_COMMAND.text, new AppMyBorrowRunnable(Label.OPTION_MY_BORROWING.text, biblioteca));
        options.put(Label.OPTION_HELP_COMMAND.text, new AppInitialRunnable(Label.OPTION_HELP.text, options));
        options.put(Label.OPTION_EXIT_COMMAND.text, new AppExitRunnable(Label.OPTION_EXIT.text));
        // Initialise initial tasks
        initialTasks = new AppInitialRunnable("", options);
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
                    RESPONSE response = selectOption(option);
                    if(response == RESPONSE.EXIT)
                        this.state = BibliotecaApp.STATE.TERMINATE;
                    break;
                case TERMINATE:
                    break;
            }
        }
    }

    public static void print(ArrayList<Rental> items, Class<? extends Rental> itemType, boolean showBorrower) {
        ArrayList<Class<? extends Rental>> printedHeader = new ArrayList<Class<? extends Rental>>();
        for(Rental item : items) {
            if(item.getClass() == itemType || itemType == Rental.class){
                // Check if header of this item type was already printed or not
                boolean isAlreadyPrintHeader = printedHeader.contains(item.getClass());
                if(!isAlreadyPrintHeader) {
                    System.out.println(item.header(showBorrower));
                    printedHeader.add(item.getClass());
                }
                System.out.println((itemType.cast(item)).info(showBorrower));
            }
        }
    }

    public RESPONSE selectOption(String command) {
        String[] commandSplit = command.split(" ", 2);
        String option = commandSplit[0];
        String parameter = commandSplit.length > 1 ? commandSplit[1] : "";
        AppRunnable selectedOption = options.getOrDefault(option, invalidOption);
        selectedOption.run(parameter);
        boolean isExitOption = selectedOption.getDescription().equals(Label.OPTION_EXIT.text);
        boolean isInvalidOption = selectedOption == invalidOption;
        if(isExitOption) return RESPONSE.EXIT;
        if(isInvalidOption) return RESPONSE.INVALID;
        return RESPONSE.VALID;
    }
}
