package com.twu.biblioteca;

import com.twu.biblioteca.database.RentalDatabase;
import com.twu.biblioteca.database.UserDatabase;
import com.twu.biblioteca.model.Rental;

public class Biblioteca {


    public enum RESPONSE {
        SUCCESS,
        AUTHORIZATION_ERROR,
        DEFAULT_ERROR
    }

    private static Biblioteca instance;
    private BibliotecaUser userManager;

    public Biblioteca() {
        instance = this;
        initialize();
    }

    public static Biblioteca getInstance() {
        if(instance == null) instance = new Biblioteca();
        return instance;
    }

    public void initialize() {
        userManager = new BibliotecaUser();
        UserDatabase.getInstance().initialize();
        RentalDatabase.getInstance().initialize();
    }

    public RESPONSE doCheckOut(String itemName) {
        if(userManager.getCurrentUser()==null) return RESPONSE.AUTHORIZATION_ERROR;
        Rental item = RentalDatabase.getInstance().getItems().stream()
                .filter(it -> it.getTitle().equals(itemName) && it.isAvailable())
                .findFirst()
                .orElse(null);
        boolean itemExist = item != null;
        if(itemExist) {
            item.doCheckOut(userManager.getCurrentUser());
            return RESPONSE.SUCCESS;
        }
        return RESPONSE.DEFAULT_ERROR;
    }

    public RESPONSE doReturn(String itemName) {
        if(userManager.getCurrentUser()==null) return RESPONSE.AUTHORIZATION_ERROR;
        Rental item = RentalDatabase.getInstance().getItems().stream()
                .filter(it -> it.getTitle().equals(itemName) && !it.isAvailable())
                .findFirst()
                .orElse(null);
        boolean itemExist = item != null;
        if(itemExist) {
            if(userManager.getCurrentUser() != item.getBorrower()) return RESPONSE.AUTHORIZATION_ERROR;
            item.doReturn();
            return RESPONSE.SUCCESS;
        }
        return RESPONSE.DEFAULT_ERROR;
    }

    public BibliotecaUser user() {
        return userManager;
    }
}
