package com.twu.biblioteca.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.BibliotecaApp;
import com.twu.biblioteca.model.AppOperation;
import com.twu.biblioteca.model.Rental;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowOperation extends AppOperation {

    private Biblioteca biblioteca;
    private Class<? extends Rental> targetClass;
    private Map<String, Biblioteca.FILTER> stringToFilter = new HashMap<String, Biblioteca.FILTER>() {{
       put("available", Biblioteca.FILTER.AVAILABLE);
       put("not available", Biblioteca.FILTER.NOT_AVAILABLE);
       put("all", Biblioteca.FILTER.ALL);
    }};

    public ShowOperation(String description, Biblioteca biblioteca) {
        this(description, biblioteca, Rental.class);
    }
    public ShowOperation(String description, Biblioteca biblioteca, Class<? extends Rental> targetClass) {
        super(description);
        this.targetClass = targetClass;
        this.biblioteca = biblioteca;
    }

    @Override
    public ArrayList<String> run(String parameter) {
        ArrayList<String> output = new ArrayList<String>();

        Biblioteca.FILTER filter = stringToFilter.getOrDefault(parameter, Biblioteca.FILTER.AVAILABLE);
        boolean showBorrower = filter != Biblioteca.FILTER.AVAILABLE;
        ArrayList<Rental> items = biblioteca.getItems(filter);
        BibliotecaApp.print(items, targetClass, showBorrower);

        response = BibliotecaApp.RESPONSE.VALID;
        return output;
    }
}
