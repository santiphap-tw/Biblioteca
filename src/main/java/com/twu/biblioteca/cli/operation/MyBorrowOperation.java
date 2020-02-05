package com.twu.biblioteca.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.BibliotecaApp;
import com.twu.biblioteca.cli.Formatter;
import com.twu.biblioteca.model.AppOperation;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.Rental;
import com.twu.biblioteca.model.User;

import java.util.ArrayList;

public class MyBorrowOperation extends AppOperation {

    public MyBorrowOperation(String description) {
        super(description);
    }

    @Override
    public ArrayList<String> run(String parameter) {
        ArrayList<String> output = new ArrayList<String>();
        ////////////
        User user = Biblioteca.getInstance().user().getCurrentUser();
        boolean isLogin = user != null;
        if(isLogin){
            output.addAll(Formatter.items(user.getItems(),Rental.class,false));
        } else {
            output.add(Label.MY_INFO_FAIL.text);
        }
        ////////////
        response = BibliotecaApp.RESPONSE.VALID;
        return output;
    }
}