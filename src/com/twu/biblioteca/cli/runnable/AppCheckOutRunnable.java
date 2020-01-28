package com.twu.biblioteca.cli.runnable;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.AppRunnable;
import com.twu.biblioteca.model.Label;

public class AppCheckOutRunnable extends AppRunnable {

    private Biblioteca biblioteca;

    public AppCheckOutRunnable(String description, Biblioteca biblioteca) {
        super(description);
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
