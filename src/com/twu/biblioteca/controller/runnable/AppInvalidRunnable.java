package com.twu.biblioteca.controller.runnable;

import com.twu.biblioteca.model.Label;

public class AppInvalidRunnable implements RunnableWithParameter{

    @Override
    public void run(String parameter) {
        run();
    }

    @Override
    public void run() {
        System.out.println(Label.OPTION_INVALID.text);
    }
}