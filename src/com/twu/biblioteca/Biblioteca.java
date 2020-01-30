package com.twu.biblioteca;

import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Movie;
import com.twu.biblioteca.model.Rental;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Biblioteca {

    public enum FILTER {
        AVAILABLE,
        NOT_AVAILABLE,
        ALL
    }

    public enum RESPONSE {
        SUCCESS,
        AUTHORIZATION_ERROR,
        DEFAULT_ERROR
    }

    private ArrayList<Rental> items;
    private BibliotecaUser userManager;

    public Biblioteca() {
        items = new ArrayList<Rental>();
        userManager = new BibliotecaUser();
        addDefaultBooks();
        addDefaultMovies();
    }

    public ArrayList<Rental> getItems(FILTER filter) {
        ArrayList<Rental> items = new ArrayList<>();
        switch (filter){
            case AVAILABLE:
                items = this.items.stream()
                        .filter(item -> item.isAvailable() == true)
                        .collect(Collectors.toCollection(ArrayList::new));
                break;
            case NOT_AVAILABLE:
                items = this.items.stream()
                        .filter(item -> item.isAvailable() == false)
                        .collect(Collectors.toCollection(ArrayList::new));
                break;
            case ALL:
                items = this.items;
                break;
        }
        // Sort items by class name
        Collections.sort(items, (o1, o2) -> o1.getClass().getName().compareTo(o2.getClass().getName()));
        return items;
    }

    public RESPONSE doCheckOut(String itemName) {
        if(userManager.getCurrentUser()==null) return RESPONSE.AUTHORIZATION_ERROR;
        Rental item = items.stream()
                .filter(it -> it.getTitle().equals(itemName))
                .findFirst()
                .orElse(null);
        boolean itemExist = item != null;
        if(itemExist) {
            if(item.isAvailable() == false) return RESPONSE.DEFAULT_ERROR;
            item.doCheckOut(userManager.getCurrentUser());
            return RESPONSE.SUCCESS;
        }
        return RESPONSE.DEFAULT_ERROR;
    }

    public RESPONSE doReturn(String itemName) {
        if(userManager.getCurrentUser()==null) return RESPONSE.AUTHORIZATION_ERROR;
        Rental item = items.stream()
                .filter(it -> it.getTitle().equals(itemName))
                .findFirst()
                .orElse(null);
        boolean itemExist = item != null;
        if(itemExist) {
            if(item.isAvailable()) return RESPONSE.DEFAULT_ERROR;
            if(userManager.getCurrentUser() != item.getBorrower()) return RESPONSE.AUTHORIZATION_ERROR;
            item.doReturn();
            return RESPONSE.SUCCESS;
        }
        return RESPONSE.DEFAULT_ERROR;
    }

    public BibliotecaUser user() {
        return userManager;
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
