package com.twu.biblioteca.cli.operation;

import com.twu.biblioteca.model.AppOperation;
import com.twu.biblioteca.model.Label;

import java.util.Map;

public class StartOperation extends AppOperation {

    private Map<String, AppOperation> options;

    public StartOperation(String description, Map<String, AppOperation> options) {
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
