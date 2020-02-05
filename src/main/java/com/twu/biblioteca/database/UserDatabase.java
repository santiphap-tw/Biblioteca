package com.twu.biblioteca.database;

import com.twu.biblioteca.model.User;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public ArrayList<User> getUsers(Predicate<? super User> filter) {
        ArrayList<User> filteredUsers = this.getUsers().stream()
                .filter(filter)
                .collect(Collectors.toCollection(ArrayList::new));
        filteredUsers.sort((o1, o2) -> o1.getClass().getName().compareTo(o2.getClass().getName()));
        return filteredUsers;
    }

    private void addDefaultUsers(){
        users.add(new User("111-1111", "1111", "Santiphap A.", "santiphap.a@mail.com", "01-111-1111"));
        users.add(new User("222-2222", "2222", "Santiphap B.", "santiphap.b@mail.com", "02-222-2222"));
        users.add(new User("333-3333", "3333", "Santiphap C.", "santiphap.c@mail.com", "03-333-3333"));
    }
}
