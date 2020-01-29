package com.twu.biblioteca.cli.runnable;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.BibliotecaApp;
import com.twu.biblioteca.model.AppRunnable;
import com.twu.biblioteca.model.Rentable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppShowRunnable extends AppRunnable {

    private Biblioteca biblioteca;
    private Class<? extends Rentable> targetClass;
    private Map<String, Biblioteca.FILTER> stringToFilter = new HashMap<String, Biblioteca.FILTER>() {{
       put("available", Biblioteca.FILTER.AVAILABLE);
       put("not available", Biblioteca.FILTER.NOT_AVAILABLE);
       put("all", Biblioteca.FILTER.ALL);
    }};

    public AppShowRunnable(String description, Biblioteca biblioteca) {
        this(description, biblioteca, Rentable.class);
    }
    public AppShowRunnable(String description, Biblioteca biblioteca, Class<? extends Rentable> targetClass) {
        super(description);
        this.targetClass = targetClass;
        this.biblioteca = biblioteca;
    }

    @Override
    public void run() {
        run("available");
    }

    @Override
    public void run(String parameter) {
        Biblioteca.FILTER filter = stringToFilter.getOrDefault(parameter, Biblioteca.FILTER.AVAILABLE);
        boolean showBorrower = filter != Biblioteca.FILTER.AVAILABLE;
        ArrayList<Rentable> items = biblioteca.getItems(filter);
        BibliotecaApp.print(items, targetClass, showBorrower);
    }
}
