package com.twu.biblioteca.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.AppOperation;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.User;

public class LogoutOperation extends AppOperation {

    private Biblioteca biblioteca;

    public LogoutOperation(String description, Biblioteca biblioteca) {
        super(description);
        this.biblioteca = biblioteca;
    }

    @Override
    public void run() {
        User user = biblioteca.user().logout();
        System.out.println(Label.LOGOUT_SUCCESS.text + user.getName());
    }

    @Override
    public void run(String parameter) {
        run();
    }
}
