package com.twu.biblioteca;


import com.twu.biblioteca.model.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionalTest {

    @Test
    public void bibliotecaShouldHaveCheckOut() {
        Biblioteca biblioteca = new Biblioteca();
        assertEquals("Biblioteca should have 6 items at start", 6, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
        biblioteca.doCheckOut("Book A");
        assertEquals("Biblioteca should have 6 items after checkout without user", 6, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
        biblioteca.user().login("111-1111", "1111");
        biblioteca.doCheckOut("Book A");
        biblioteca.doCheckOut("Movie A");
        assertEquals("Biblioteca should have 4 item after checkout with user", 4, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
    }

    @Test
    public void bibliotecaShouldHaveReturn() {
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
    public void bibliotecaShouldHaveUserAccess() {
        Biblioteca biblioteca = new Biblioteca();
        assertEquals("Biblioteca should have users list", 3, biblioteca.user().getUsers().size());
        assertEquals("Biblioteca should have no current user", null, biblioteca.user().getCurrentUser());
        biblioteca.user().login("111-1111", "1111");
        assertEquals("Biblioteca should have the current user", "111-1111", biblioteca.user().getCurrentUser().getId());
        biblioteca.user().logout();
        assertEquals("Biblioteca should have no current user", null, biblioteca.user().getCurrentUser());
    }

    @Test
    public void rentableShouldHaveCheckOutAndReturn() {
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
    public void bookShouldHaveInformation() {
        Book book = new Book("Title","Author","Date");
        assertEquals("Book should have a title", "Title", book.getTitle());
        assertEquals("Book should have an author", "Author", book.getAuthor());
        assertEquals("Book should have a publish date", "Date", book.getPublishDate());
        assertEquals("Book should have a borrower", null, book.getBorrower());
    }

    @Test
    public void movieShouldHaveInformation() {
        Movie movie = new Movie("Title",2020,"Director",10);
        assertEquals("Movie should have a title", "Title", movie.getTitle());
        assertEquals("Movie should have a year", 2020, movie.getYear());
        assertEquals("Movie should have a director", "Director", movie.getDirector());
        assertEquals("Movie should have a rating", 10, movie.getRating());
        assertEquals("Movie should have a borrower", null, movie.getBorrower());
    }

    @Test
    public void UserShouldHaveInformation() {
        User user = new User("111-1111", "1234", "Name", "Email", "Phone");
        assertEquals("User should have id", "111-1111", user.getId());
        assertEquals("User should have password", "1234", user.getPassword());
        assertEquals("User should have name", "Name", user.getName());
        assertEquals("User should have email", "Email", user.getEmail());
        assertEquals("User should have phone", "Phone", user.getPhone());
    }

    @Test
    public void UserShouldHaveCheckOutandReturnItems() {
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
