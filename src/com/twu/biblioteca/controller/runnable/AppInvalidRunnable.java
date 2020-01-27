package com.twu.biblioteca.controller.runnable;

import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.RunnableWithParameter;

public class AppInvalidRunnable implements RunnableWithParameter {

    @Override
    public void run(String parameter) {
        run();
    }

    @Override
    public void run() {
        System.out.println(Label.OPTION_INVALID.text);
    }
}