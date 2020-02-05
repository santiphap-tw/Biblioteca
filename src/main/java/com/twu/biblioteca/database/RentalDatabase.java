package com.twu.biblioteca.database;

import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Movie;
import com.twu.biblioteca.model.Rental;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RentalDatabase {

    private static RentalDatabase instance;
    private ArrayList<Rental> items;

    public RentalDatabase() {
        instance = this;
        initialize();
    }

    public static RentalDatabase getInstance() {
        if(instance == null) instance = new RentalDatabase();
        return instance;
    }

    public void initialize() {
        items = new ArrayList<>();
        addDefaultBooks();
        addDefaultMovies();
    }

    public ArrayList<Rental> getItems() {
        items.sort((o1, o2) -> o1.getClass().getName().compareTo(o2.getClass().getName()));
        return items;
    }

    public ArrayList<Rental> getItems(Predicate<? super Rental> filter) {
        ArrayList<Rental> filteredItems = this.getItems().stream()
                .filter(filter)
                .collect(Collectors.toCollection(ArrayList::new));
        filteredItems.sort((o1, o2) -> o1.getClass().getName().compareTo(o2.getClass().getName()));
        return filteredItems;
    }

    private void addDefaultBooks(){
        items.add(new Book("Book A", "Santiphap A.", "01/01/2008"));
        items.add(new Book("Book B", "Santiphap B.", "02/01/2008"));
        items.add(new Book("Book C", "Santiphap C.", "03/01/2008"));
    }

    private void addDefaultMovies(){
        items.add(new Movie("Movie A", 2008, "Santiphap A.", 8));
        items.add(new Movie("Movie B", 2013,"Santiphap B.", 9));
        items.add(new Movie("Movie C", 2020, "Santiphap C.", 10));
    }
}
