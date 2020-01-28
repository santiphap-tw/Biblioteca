package com.twu.biblioteca.model;

import java.util.ArrayList;

public class User {
    private String id;
    private String password;
    private String name;
    private String email;
    private String phone;
    private ArrayList<Rentable> items;

    public User(String id, String password, String name, String email, String phone) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.items = new ArrayList<Rentable>();
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

    public ArrayList<Rentable> getItems() {
        return items;
    }

    public void checkOutItem(Rentable item) {
        this.items.add(item);
    }

    public boolean returnItem(Rentable item) {
        return this.items.remove(item);
    }
}
