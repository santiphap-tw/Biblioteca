package com.twu.biblioteca.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.AppOperation;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.User;

public class MyInfoOperation extends AppOperation {

    private Biblioteca biblioteca;

    public MyInfoOperation(String description, Biblioteca biblioteca) {
        super(description);
        this.biblioteca = biblioteca;
    }

    @Override
    public void run() {
        User user = biblioteca.user().getCurrentUser();
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