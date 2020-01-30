package com.twu.biblioteca.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.BibliotecaApp;
import com.twu.biblioteca.model.AppOperation;
import com.twu.biblioteca.model.Label;

import java.util.ArrayList;

public class CheckOutOperation extends AppOperation {

    private Biblioteca biblioteca;

    public CheckOutOperation(String description, Biblioteca biblioteca) {
        super(description);
        this.biblioteca = biblioteca;
    }

    @Override
    public ArrayList<String> run(String itemName) {
        ArrayList<String> output = new ArrayList<String>();

        Biblioteca.RESPONSE isSuccess = biblioteca.doCheckOut(itemName.trim());
        if(isSuccess == Biblioteca.RESPONSE.SUCCESS)
            output.add(Label.CHECKOUT_SUCCESS.text);
        else if(isSuccess == Biblioteca.RESPONSE.DEFAULT_ERROR)
            output.add(Label.CHECKOUT_FAIL.text);
        else if(isSuccess == Biblioteca.RESPONSE.AUTHORIZATION_ERROR)
            output.add(Label.AUTHORIZATION_ERROR.text);

        response = BibliotecaApp.RESPONSE.VALID;
        return output;
    }
}
