package com.twu.biblioteca.cli.runnable;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.RunnableWithParameter;

public class AppReturnRunnable implements RunnableWithParameter {

    private Biblioteca biblioteca;

    public AppReturnRunnable(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    @Override
    public void run() {
        run("");
    }

    @Override
    public void run(String itemName) {
        Biblioteca.RESPONSE isSuccess = biblioteca.doReturn(itemName.trim());
        if(isSuccess == Biblioteca.RESPONSE.SUCCESS)
            System.out.println(Label.RETURN_SUCCESS.text);
        else if(isSuccess == Biblioteca.RESPONSE.DEFAULT_ERROR)
            System.out.println(Label.RETURN_FAIL.text);
        else if(isSuccess == Biblioteca.RESPONSE.AUTHORIZATION_ERROR)
            System.out.println(Label.AUTHORIZATION_ERROR.text);
    }
}
