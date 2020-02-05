package com.twu.biblioteca.cli.operation;

import com.twu.biblioteca.cli.BibliotecaApp;
import com.twu.biblioteca.cli.Formatter;
import com.twu.biblioteca.database.RentalDatabase;
import com.twu.biblioteca.model.AppOperation;
import com.twu.biblioteca.model.Rental;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class ShowOperation extends AppOperation {

    private Class<? extends Rental> targetClass;
    private Map<String, Predicate<? super Rental>> stringToFilter = new HashMap<String, Predicate<? super Rental>>() {{
       put("available", RentalDatabase.Filter.AVAILABLE);
       put("not available", RentalDatabase.Filter.NOT_AVAILABLE);
       put("all", RentalDatabase.Filter.ALL);
    }};

    public ShowOperation(String description) {
        this(description, Rental.class);
    }
    public ShowOperation(String description, Class<? extends Rental> targetClass) {
        super(description);
        this.targetClass = targetClass;
    }

    @Override
    public ArrayList<String> run(String parameter) {
        ArrayList<String> output = new ArrayList<String>();
        ////////////
        Predicate<? super Rental> filter = stringToFilter.getOrDefault(parameter, RentalDatabase.Filter.AVAILABLE);
        boolean showBorrower = !filter.equals(RentalDatabase.Filter.AVAILABLE);
        filter = filter.and(item -> targetClass.isInstance(item));
        ArrayList<Rental> items = RentalDatabase.getInstance().getItems(filter);
        output.addAll(Formatter.collection(items,targetClass,showBorrower));
        ////////////
        response = BibliotecaApp.RESPONSE.VALID;
        return output;
    }
}
