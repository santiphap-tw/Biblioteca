package com.twu.biblioteca.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.BibliotecaApp;
import com.twu.biblioteca.model.AppOperation;
import com.twu.biblioteca.model.Label;

import java.util.ArrayList;

public class CheckOutOperation extends AppOperation {

    public CheckOutOperation(String description) {
        super(description);
    }

    @Override
    public ArrayList<String> run(String itemName) {
        ArrayList<String> output = new ArrayList<String>();
        ////////////
        Biblioteca.RESPONSE isSuccess = Biblioteca.getInstance().doCheckOut(itemName.trim());
        switch (isSuccess) {
            case SUCCESS:
                output.add(Label.CHECKOUT_SUCCESS.text);
                break;
            case DEFAULT_ERROR:
                output.add(Label.CHECKOUT_FAIL.text);
                break;
            case AUTHORIZATION_ERROR:
                output.add(Label.AUTHORIZATION_ERROR.text);
        }
        ////////////
        response = BibliotecaApp.RESPONSE.VALID;
        return output;
    }
}
