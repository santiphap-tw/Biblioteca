package com.twu.biblioteca.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.BibliotecaApp;
import com.twu.biblioteca.cli.ItemPrinter;
import com.twu.biblioteca.model.AppOperation;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.Rental;
import com.twu.biblioteca.model.User;

import java.util.ArrayList;

public class MyBorrowOperation extends AppOperation {

    private Biblioteca biblioteca;

    public MyBorrowOperation(String description, Biblioteca biblioteca) {
        super(description);
        this.biblioteca = biblioteca;
    }

    @Override
    public ArrayList<String> run(String parameter) {
        ArrayList<String> output = new ArrayList<String>();
        ////////////
        User user = biblioteca.user().getCurrentUser();
        boolean isLogin = user != null;
        if(isLogin){
            output.addAll(ItemPrinter.collection(user.getItems(),Rental.class,false));
        } else {
            output.add(Label.MY_INFO_FAIL.text);
        }
        ////////////
        response = BibliotecaApp.RESPONSE.VALID;
        return output;
    }
}