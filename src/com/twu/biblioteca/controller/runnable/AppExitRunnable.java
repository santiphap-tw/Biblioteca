package com.twu.biblioteca.controller.runnable;

import com.twu.biblioteca.model.Label;

public class AppExitRunnable implements RunnableWithParameter{

    @Override
    public void run() {
        System.out.println(Label.EXIT.text);
    }

    @Override
    public void run(String parameter) {
        run();
    }
}
