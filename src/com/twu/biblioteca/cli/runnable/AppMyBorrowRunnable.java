package com.twu.biblioteca.cli.runnable;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.BibliotecaApp;
import com.twu.biblioteca.model.*;

public class AppMyBorrowRunnable extends AppRunnable {

    private Biblioteca biblioteca;

    public AppMyBorrowRunnable(String description, Biblioteca biblioteca) {
        super(description);
        this.biblioteca = biblioteca;
    }

    @Override
    public void run() {
        User user = biblioteca.user().getCurrentUser();
        boolean isLogin = user != null;
        if(isLogin){
            BibliotecaApp.print(user.getItems(), Rental.class, false);
        } else {
            System.out.println(Label.MY_INFO_FAIL.text);
        }
    }

    @Override
    public void run(String parameter) {
        run();
    }
}