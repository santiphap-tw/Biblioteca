package com.twu.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;

public class User {
    private String id;
    private String password;
    private String name;
    private String email;
    private String phone;
    private ArrayList<Rental> items;

    public User(String id, String password, String name, String email, String phone) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.items = new ArrayList<Rental>();
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

    @JsonManagedReference
    public ArrayList<Rental> getItems() {
        // Sort items by class name
        items.sort((o1, o2) -> o1.getClass().getName().compareTo(o2.getClass().getName()));
        return items;
    }

    public void checkOutItem(Rental item) {
        this.items.add(item);
    }

    public void returnItem(Rental item) {
        this.items.remove(item);
    }
}
