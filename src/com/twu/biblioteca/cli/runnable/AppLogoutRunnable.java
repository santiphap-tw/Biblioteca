package com.twu.biblioteca.cli.runnable;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.AppRunnable;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.User;

public class AppLogoutRunnable extends AppRunnable {

    private Biblioteca biblioteca;

    public AppLogoutRunnable(String description, Biblioteca biblioteca) {
        super(description);
        this.biblioteca = biblioteca;
    }

    @Override
    public void run() {
        User user = biblioteca.logout();
        System.out.println(Label.LOGOUT_SUCCESS.text + user.getName());
    }

    @Override
    public void run(String parameter) {
        run();
    }
}
