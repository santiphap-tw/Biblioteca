package com.twu.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.twu.biblioteca.database.RentalDatabase;

import java.util.ArrayList;

public class User {
    private String id;
    private String password;
    private String name;
    private String email;
    private String phone;

    public User(String id, String password, String name, String email, String phone) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @JsonIgnoreProperties("borrower")
    public ArrayList<Rental> getItems() {
        return RentalDatabase.getInstance().getItems(item -> item.borrower == this);
    }
}
