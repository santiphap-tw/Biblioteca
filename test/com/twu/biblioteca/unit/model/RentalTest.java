package com.twu.biblioteca.unit.model;

import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Rental;
import com.twu.biblioteca.model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RentalTest {

    private Rental rental;

    @Before
    public void initialize() {
        // Given
        rental = new Book("Test");
    }

    @Test
    public void getTitleTest() {
        // Getter method
    }

    @Test
    public void isAvailableTest() {
        // Getter method
    }

    @Test
    public void doCheckOut() {
        // Given
        User user = new User("111-1111", "1111", "Name", "Email", "Phone");
        // When
        rental.doCheckOut(user);
        // Then
        assertEquals("rental should not available after checkout", false, rental.isAvailable());
        assertEquals("borrower should be the same user", user, rental.getBorrower());
    }

    @Test
    public void doReturn() {
        // Given
        User user = new User("111-1111", "1111", "Name", "Email", "Phone");
        rental.doCheckOut(user);
        // When
        rental.doReturn();
        // Then
        assertEquals("rental should available after return", true, rental.isAvailable());
        assertEquals("borrower should be null", null, rental.getBorrower());
    }

    @Test
    public void getBorrower() {
        // Getter method
    }
}
