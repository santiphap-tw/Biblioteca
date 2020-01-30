package com.twu.biblioteca.unit;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.Rental;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class BibliotecaTest {

    private Biblioteca biblioteca;

    @Before
    public void initialize() {
        // Given
        biblioteca = new Biblioteca();
    }

    @Test
    public void getItemsTest() {
        // Given
        biblioteca.user().login("111-1111", "1111");
        biblioteca.doCheckOut("Book A");
        ArrayList<Rental> items = new ArrayList<Rental>();

        // Available Test
        // When
        items = biblioteca.getItems(Biblioteca.FILTER.AVAILABLE);
        // Then
        boolean isAllItemAvailable = items.stream().allMatch(item -> item.isAvailable() == true);
        assertEquals("getItems AVAILABLE should return only available items", true, isAllItemAvailable);

        // Not Available Test
        // When
        items = biblioteca.getItems(Biblioteca.FILTER.NOT_AVAILABLE);
        // Then
        boolean isAllItemNotAvailable = items.stream().allMatch(item -> item.isAvailable() == false);
        assertEquals("getItems NOT_AVAILABLE should return only not available items", true, isAllItemNotAvailable);

        // All Test
        // When
        items = biblioteca.getItems(Biblioteca.FILTER.ALL);
        // Then
        boolean isSomeItemAvailable = items.stream().anyMatch(item -> item.isAvailable() == true);
        boolean isSomeItemNotAvailable = items.stream().anyMatch(item -> !item.isAvailable() == false);
        boolean isShowAllItem = isSomeItemAvailable & isSomeItemNotAvailable;
        assertEquals("getItems ALL should return both available and not available items", true, isShowAllItem);
    }

    @Test
    public void doCheckOutTest() {
        // Given
        biblioteca.user().login("111-1111", "1111");

        // Positive Testing
        // When
        Biblioteca.RESPONSE response = biblioteca.doCheckOut("Book A");
        // Then
        assertEquals("doCheckOut should response success when checkout available item", Biblioteca.RESPONSE.SUCCESS, response);

        // Negative Testing
        // When
        response = biblioteca.doCheckOut("Book A");
        // Then
        assertEquals("doCheckOut should response default error when checkout non-available item", Biblioteca.RESPONSE.DEFAULT_ERROR, response);
        // When
        response = biblioteca.doCheckOut("Book Z");
        // Then
        assertEquals("doCheckOut should response default error when checkout invalid item", Biblioteca.RESPONSE.DEFAULT_ERROR, response);
    }

    @Test
    public void doReturnTest() {
        // Given
        biblioteca.user().login("111-1111", "1111");
        biblioteca.doCheckOut("Book A");
        biblioteca.doCheckOut("Book B");

        // Positive Testing
        // When
        Biblioteca.RESPONSE response = biblioteca.doReturn("Book A");
        // Then
        assertEquals("doReturn should response success when return correct item", Biblioteca.RESPONSE.SUCCESS, response);


        //Negative Testing - Wrong item
        // When
        response = biblioteca.doReturn("Book A");
        // Then
        assertEquals("doReturn should response default error when return available item", Biblioteca.RESPONSE.DEFAULT_ERROR, response);
        // When
        response = biblioteca.doReturn("Book Z");
        // Then
        assertEquals("doReturn should response default error when return invalid item", Biblioteca.RESPONSE.DEFAULT_ERROR, response);

        //Negative Testing - Wrong user
        // Given
        biblioteca.user().login("222-2222", "2222");
        // When
        response = biblioteca.doReturn("Book B");
        // Then
        assertEquals("doReturn should response authorization error when return item of different user", Biblioteca.RESPONSE.AUTHORIZATION_ERROR, response);
    }

    @Test
    public void userTest() {
        // Getter method
    }
}
