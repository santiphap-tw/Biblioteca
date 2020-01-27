package com.twu.biblioteca.cli.runnable;

import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.RunnableWithParameter;

public class AppExitRunnable implements RunnableWithParameter {

    @Override
    public void run() {
        System.out.println(Label.EXIT.text);
    }

    @Override
    public void run(String parameter) {
        run();
    }
}
