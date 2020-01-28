package com.twu.biblioteca;

import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Movie;
import com.twu.biblioteca.model.Rentable;
import com.twu.biblioteca.model.User;

import java.util.*;

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

    private ArrayList<Rentable> items;
    private ArrayList<User> users;
    private User currentUser;

    public Biblioteca() {
        items = new ArrayList<Rentable>();
        users = new ArrayList<User>();
        addDefaultBooks();
        addDefaultMovies();
        addDefaultUsers();
        currentUser = null;
    }

    public ArrayList<Rentable> getItems(FILTER filter) {
        ArrayList<Rentable> items = new ArrayList<Rentable>();
        for(Rentable item : this.items){
            switch (filter){
                case AVAILABLE:
                    if(item.isAvailable()) items.add(item);
                    break;
                case NOT_AVAILABLE:
                    if(!item.isAvailable()) items.add(item);
                    break;
                case ALL:
                    items.add(item);
                    break;
            }
        }
        return items;
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

    public RESPONSE doCheckOut(String itemName) {
        if(currentUser==null) return RESPONSE.AUTHORIZATION_ERROR;
        for(Rentable item : this.items) {
            if(itemName.equals(item.getTitle())){
                if(!item.isAvailable()) return RESPONSE.DEFAULT_ERROR;
                item.doCheckOut(currentUser);
                return RESPONSE.SUCCESS;
            }
        }
        return RESPONSE.DEFAULT_ERROR;
    }

    public RESPONSE doReturn(String itemName) {
        if(currentUser==null) return RESPONSE.AUTHORIZATION_ERROR;
        for(Rentable item : this.items) {
            if(itemName.equals(item.getTitle())){
                if(item.isAvailable()) return RESPONSE.DEFAULT_ERROR;
                if(currentUser != item.getBorrower()) return RESPONSE.AUTHORIZATION_ERROR;
                item.doReturn();
                return RESPONSE.SUCCESS;
            }
        }
        return RESPONSE.DEFAULT_ERROR;
    }

    public ArrayList<Rentable> getMyItems() {
        if(currentUser == null) return new ArrayList<Rentable>();
        return currentUser.getItems();
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
    private void addDefaultUsers(){
        users.add(new User("111-1111", "1111", "Santiphap A.", "santiphap.a@mail.com", "01-111-1111"));
        users.add(new User("222-2222", "2222", "Santiphap B.", "santiphap.b@mail.com", "02-222-2222"));
        users.add(new User("333-3333", "3333", "Santiphap C.", "santiphap.c@mail.com", "03-333-3333"));
    }
}
