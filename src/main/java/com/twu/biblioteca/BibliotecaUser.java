package com.twu.biblioteca;

import com.twu.biblioteca.database.UserDatabase;
import com.twu.biblioteca.model.User;

public class BibliotecaUser {

    private User currentUser;

    public BibliotecaUser() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User login(String id, String password) {
        User user = UserDatabase.getInstance().getUsers().stream()
                .filter(u -> u.getId().equals(id) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
        currentUser = user;
        return user;
    }

    public User logout() {
        User user = currentUser;
        currentUser = null;
        return user;
    }
}
