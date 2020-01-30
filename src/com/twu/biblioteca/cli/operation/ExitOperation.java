package com.twu.biblioteca.cli.operation;

import com.twu.biblioteca.model.AppOperation;
import com.twu.biblioteca.model.Label;

public class ExitOperation extends AppOperation {

    public ExitOperation(String description) {
        super(description);
    }

    @Override
    public void run() {
        System.out.println(Label.EXIT.text);
    }

    @Override
    public void run(String parameter) {
        run();
    }
}
