package com.twu.biblioteca.controller.runnable;

import com.twu.biblioteca.controller.Biblioteca;
import com.twu.biblioteca.model.Label;

public class AppCheckOutRunnable implements RunnableWithParameter{

    private Biblioteca biblioteca;

    public AppCheckOutRunnable(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    @Override
    public void run() {
        run("");
    }

    @Override
    public void run(String bookName) {
        boolean isSuccess = biblioteca.doCheckOut(bookName.trim());
        if(isSuccess)
            System.out.println(Label.CHECKOUT_SUCCESS.text);
        else
            System.out.println(Label.CHECKOUT_FAIL.text);
    }
}
