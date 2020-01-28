package com.twu.biblioteca.cli.runnable;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.RunnableWithParameter;
import com.twu.biblioteca.model.User;

public class AppLoginRunnable implements RunnableWithParameter {

    private Biblioteca biblioteca;

    public AppLoginRunnable(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    @Override
    public void run() {
        run("");
    }

    @Override
    public void run(String idAndPassword) {
        String[] idAndPasswordSplit = idAndPassword.split(" ", 2);
        String id = idAndPasswordSplit[0];
        String password = idAndPasswordSplit.length > 1 ? idAndPasswordSplit[1] : "";
        User user = biblioteca.login(id, password);
        boolean loginSuccess = user != null;
        if(loginSuccess)
            System.out.println(Label.LOGIN_SUCCESS.text + user.getName());
        else
            System.out.println(Label.LOGIN_FAIL.text);
    }
}
