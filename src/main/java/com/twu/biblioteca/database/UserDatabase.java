package com.twu.biblioteca.database;

import com.twu.biblioteca.model.User;

import java.util.ArrayList;

public class UserDatabase {

    private static UserDatabase instance;
    private ArrayList<User> users;

    public UserDatabase() {
        instance = this;
        initialize();
    }

    public static UserDatabase getInstance() {
        if(instance == null) instance = new UserDatabase();
        return instance;
    }

    public void initialize() {
        users = new ArrayList<>();
        addDefaultUsers();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    private void addDefaultUsers(){
        users.add(new User("111-1111", "1111", "Santiphap A.", "santiphap.a@mail.com", "01-111-1111"));
        users.add(new User("222-2222", "2222", "Santiphap B.", "santiphap.b@mail.com", "02-222-2222"));
        users.add(new User("333-3333", "3333", "Santiphap C.", "santiphap.c@mail.com", "03-333-3333"));
    }
}
