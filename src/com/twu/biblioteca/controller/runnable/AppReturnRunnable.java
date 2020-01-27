package com.twu.biblioteca.controller.runnable;

import com.twu.biblioteca.controller.Biblioteca;
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
    public void run(String bookName) {
        boolean isSuccess = biblioteca.doReturn(bookName.trim());
        if(isSuccess)
            System.out.println(Label.RETURN_SUCCESS.text);
        else
            System.out.println(Label.RETURN_FAIL.text);
    }
}
