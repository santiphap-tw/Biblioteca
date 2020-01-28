package com.twu.biblioteca.cli.runnable;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.RunnableWithParameter;

public class AppCheckOutRunnable implements RunnableWithParameter {

    private Biblioteca biblioteca;

    public AppCheckOutRunnable(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    @Override
    public void run() {
        run("");
    }

    @Override
    public void run(String itemName) {
        Biblioteca.RESPONSE isSuccess = biblioteca.doCheckOut(itemName.trim());
        if(isSuccess == Biblioteca.RESPONSE.SUCCESS)
            System.out.println(Label.CHECKOUT_SUCCESS.text);
        else if(isSuccess == Biblioteca.RESPONSE.DEFAULT_ERROR)
            System.out.println(Label.CHECKOUT_FAIL.text);
        else if(isSuccess == Biblioteca.RESPONSE.AUTHORIZATION_ERROR)
            System.out.println(Label.AUTHORIZATION_ERROR.text);
    }
}
