package com.twu.biblioteca.cli.operation;

import com.twu.biblioteca.cli.BibliotecaApp;
import com.twu.biblioteca.model.AppOperation;
import com.twu.biblioteca.model.Label;

import java.util.ArrayList;

public class InvalidOperation extends AppOperation {

    public InvalidOperation(String description) {
        super(description);
    }

    @Override
    public ArrayList<String> run(String parameter) {
        ArrayList<String> output = new ArrayList<String>();
        output.add(Label.OPTION_INVALID.text);
        response = BibliotecaApp.RESPONSE.INVALID;
        return output;
    }
}