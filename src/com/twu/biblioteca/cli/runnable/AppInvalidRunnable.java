package com.twu.biblioteca.cli.runnable;

import com.twu.biblioteca.model.AppRunnable;
import com.twu.biblioteca.model.Label;

public class AppInvalidRunnable extends AppRunnable {

    public AppInvalidRunnable(String description) {
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