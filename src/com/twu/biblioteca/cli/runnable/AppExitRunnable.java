package com.twu.biblioteca.cli.runnable;

import com.twu.biblioteca.model.AppRunnable;
import com.twu.biblioteca.model.Label;

public class AppExitRunnable extends AppRunnable {

    public AppExitRunnable(String description) {
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
