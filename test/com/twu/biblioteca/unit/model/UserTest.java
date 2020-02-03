package com.twu.biblioteca.unit.model;

import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Movie;
import com.twu.biblioteca.model.Rental;
import com.twu.biblioteca.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class UserTest {

    public User user;

    @Before
    public void initialize() {
        // Given
        user = new User("111-1111", "1111", "Name", "Email", "Phone");
    }

    @Test
    public void shouldGetItem() {
        // Given
        Book book1 = new Book("Book1");
        Book book2 = new Book("Book2");
        Movie movie = new Movie("Movie");
        user.checkOutItem(book1);
        user.checkOutItem(movie);
        user.checkOutItem(book2);
        // When
        ArrayList<Rental> items = user.getItems();
        // Then
        ArrayList<Rental> expectedItemOrder = new ArrayList<Rental>();
        expectedItemOrder.add(book1);
        expectedItemOrder.add(book2);
        expectedItemOrder.add(movie);
        assertEquals("getItems should be sorted", expectedItemOrder, items);
    }

    @Test
    public void shouldCheckOut() {
        // Given
        Book book = new Book("Book");
        // When
        user.checkOutItem(book);
        // Then
        assertEquals("user should have 1 item", 1, user.getItems().size());
    }

    @Test
    public void shouldReturnCorrectItem() {
        // Given
        Book book = new Book("Book");
        Movie movie = new Movie("Movie");
        user.checkOutItem(book);
        user.checkOutItem(movie);
        // When
        user.returnItem(book);
        // Then
        assertEquals("user should have 1 item from 2 after return correct item", 1, user.getItems().size());
    }

    @Test
    public void shouldNotReturnWrongItem() {
        // Given
        Book book = new Book("Book");
        Movie movie = new Movie("Movie");
        user.checkOutItem(movie);
        // When
        user.returnItem(book);
        // Then
        assertEquals("user should have 1 item from 1 after return wrong item", 1, user.getItems().size());
    }
}
