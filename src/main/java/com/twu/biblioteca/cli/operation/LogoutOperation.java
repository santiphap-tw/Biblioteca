package com.twu.biblioteca.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.AppOperation;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.User;

import java.util.ArrayList;

public class LogoutOperation extends AppOperation {

    public LogoutOperation(String description) {
        super(description);
    }

    @Override
    public ArrayList<String> run(String parameter) {
        ArrayList<String> output = new ArrayList<String>();
        ////////////
        boolean isLoggedIn = Biblioteca.getInstance().user().getCurrentUser() != null;
        if(isLoggedIn) {
            User user = Biblioteca.getInstance().user().logout();
            output.add(Label.LOGOUT_SUCCESS.text + user.getName());
        }
        else {
            output.add(Label.MY_INFO_FAIL.text);
        }
        ////////////
        return output;
    }
}
