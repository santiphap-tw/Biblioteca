package com.twu.biblioteca.cli.runnable;

import com.twu.biblioteca.model.AppRunnable;
import com.twu.biblioteca.model.Label;

import java.util.Map;

public class AppInitialRunnable extends AppRunnable {

    private Map<String, AppRunnable> options;

    public AppInitialRunnable(String description, Map<String, AppRunnable> options) {
        super(description);
        this.options = options;
    }

    @Override
    public void run() {
        showWelcomeMessage();
        showOptions();
    }

    @Override
    public void run(String parameter) {
        showOptions();
    }

    private void showWelcomeMessage() {
        System.out.println(Label.WELCOME.text);
    }

    private void showOptions() {
        System.out.println(Label.OPTION_PROMPT.text);
        options.forEach((command, option) -> {
            System.out.println(command+ "\t\t" + option.getDescription());
        });
    }
}
