package com.twu.biblioteca;

import com.twu.biblioteca.model.Rentable;
import com.twu.biblioteca.model.User;

import java.util.ArrayList;

public class BIbliotecaUser {
    private ArrayList<User> users;
    private User currentUser;


    public BIbliotecaUser() {
        users = new ArrayList<User>();
        addDefaultUsers();
        currentUser = null;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User login(String id, String password) {
        for(User user : this.users){
            if(user.getId().equals(id) && user.getPassword().equals(password)){
                currentUser = user;
                return user;
            }
        }
        return null;
    }

    public User logout() {
        User user = currentUser;
        currentUser = null;
        return user;
    }

    private void addDefaultUsers(){
        users.add(new User("111-1111", "1111", "Santiphap A.", "santiphap.a@mail.com", "01-111-1111"));
        users.add(new User("222-2222", "2222", "Santiphap B.", "santiphap.b@mail.com", "02-222-2222"));
        users.add(new User("333-3333", "3333", "Santiphap C.", "santiphap.c@mail.com", "03-333-3333"));
    }
}
