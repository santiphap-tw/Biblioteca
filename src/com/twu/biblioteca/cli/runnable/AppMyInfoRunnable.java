package com.twu.biblioteca.cli.runnable;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.AppRunnable;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.User;

public class AppMyInfoRunnable extends AppRunnable {

    private Biblioteca biblioteca;

    public AppMyInfoRunnable(String description, Biblioteca biblioteca) {
        super(description);
        this.biblioteca = biblioteca;
    }

    @Override
    public void run() {
        User user = biblioteca.getCurrentUser();
        boolean isLogin = user != null;
        if(isLogin){
            System.out.println("ID: \t" + user.getId());
            System.out.println("Name: \t" + user.getName());
            System.out.println("Email: \t" + user.getEmail());
            System.out.println("Phone: \t" + user.getPhone());
        } else {
            System.out.println(Label.MY_INFO_FAIL.text);
        }
    }

    @Override
    public void run(String parameter) {
        run();
    }
}