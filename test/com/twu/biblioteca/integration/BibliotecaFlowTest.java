package com.twu.biblioteca.integration;


import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BibliotecaFlowTest {

    @Test
    public void bibliotecaCheckOutAndCheckInFlow() {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.user().login("111-1111", "1111");
        biblioteca.doCheckOut("Book A");
        biblioteca.doCheckOut("Movie A");
        assertEquals("Biblioteca should have 4 items ", 4, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
        biblioteca.user().logout();
        biblioteca.doReturn("Book A");
        assertEquals("Biblioteca should have 4 item after return with no user", 4, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
        biblioteca.user().login("111-1111", "1111");
        biblioteca.doReturn("Book A");
        assertEquals("Biblioteca should have 5 items after return with user", 5, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
        biblioteca.user().login("222-2222", "2222");
        biblioteca.doReturn("Movie A");
        assertEquals("Biblioteca should have 5 items after return with wrong user", 5, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
    }

    @Test
    public void bibliotecaLoginAndLogoutFlow() {
        Biblioteca biblioteca = new Biblioteca();
        assertEquals("Biblioteca should have users list", 3, biblioteca.user().getUsers().size());
        assertEquals("Biblioteca should have no current user", null, biblioteca.user().getCurrentUser());
        biblioteca.user().login("111-1111", "1111");
        assertEquals("Biblioteca should have the current user", "111-1111", biblioteca.user().getCurrentUser().getId());
        biblioteca.user().logout();
        assertEquals("Biblioteca should have no current user", null, biblioteca.user().getCurrentUser());
    }

    @Test
    public void rentalCheckOutAndReturnFlow() {
        Rental item = new Book("Title","Author","Date");
        User user = new User("111-1111", "1234", "Name", "Email", "Phone");
        assertEquals("Book should available", true, item.isAvailable());
        item.doCheckOut(user);
        assertEquals("Book should not available after check out", false, item.isAvailable());
        assertEquals("Borrower should be user 111-1111", user.getId(), item.getBorrower().getId());
        item.doReturn();
        assertEquals("Book should available after return", true, item.isAvailable());
        assertEquals("Borrower should be none", null, item.getBorrower());
    }

    @Test
    public void userCheckOutAndReturnFlow() {
        User user = new User("111-1111", "1234", "Name", "Email", "Phone");
        Book book = new Book("Title","Author","Date");
        Movie movie = new Movie("Title",2020,"Director",10);
        user.checkOutItem(book);
        user.checkOutItem(movie);
        assertEquals("User should have 2 items", 2, user.getItems().size());
        user.returnItem(book);
        user.returnItem(movie);
        assertEquals("User should have no item", 0, user.getItems().size());
    }
}
