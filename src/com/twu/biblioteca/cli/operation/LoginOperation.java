package com.twu.biblioteca.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.BibliotecaApp;
import com.twu.biblioteca.model.AppOperation;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.User;

import java.util.ArrayList;

public class LoginOperation extends AppOperation {

    private Biblioteca biblioteca;

    public LoginOperation(String description, Biblioteca biblioteca) {
        super(description);
        this.biblioteca = biblioteca;
    }

    @Override
    public ArrayList<String> run(String idAndPassword) {
        ArrayList<String> output = new ArrayList<String>();
        ////////////
        String[] idAndPasswordSplit = idAndPassword.split(" ", 2);
        String id = idAndPasswordSplit[0];
        String password = idAndPasswordSplit.length > 1 ? idAndPasswordSplit[1] : "";
        biblioteca.user().login(id, password);
        boolean loginSuccess = (biblioteca.user().getCurrentUser() != null);
        if(loginSuccess)
            output.add(Label.LOGIN_SUCCESS.text + biblioteca.user().getCurrentUser().getName());
        else
            output.add(Label.LOGIN_FAIL.text);
        ////////////
        response = BibliotecaApp.RESPONSE.VALID;
        return output;
    }
}
