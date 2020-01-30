package com.twu.biblioteca.cli.operation;

import com.twu.biblioteca.model.AppOperation;
import com.twu.biblioteca.model.Label;

public class InvalidOperation extends AppOperation {

    public InvalidOperation(String description) {
        super(description);
    }

    @Override
    public void run(String parameter) {
        run();
    }

    @Override
    public void run() {
        System.out.println(Label.OPTION_INVALID.text);
    }
}