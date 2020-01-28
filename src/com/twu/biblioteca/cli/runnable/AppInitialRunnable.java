package com.twu.biblioteca.cli.runnable;

import com.twu.biblioteca.model.AppOption;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.RunnableWithParameter;

import java.util.Map;

public class AppInitialRunnable implements RunnableWithParameter {

    private Map<String,AppOption> options;

    public AppInitialRunnable(Map<String, AppOption> options) {
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
