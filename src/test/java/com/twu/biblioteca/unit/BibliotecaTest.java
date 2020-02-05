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
    public void shouldGetAvailableItems() {
        // Given
        biblioteca.user().login("111-1111", "1111");
        biblioteca.doCheckOut("Book A");
        // When
        ArrayList<Rental> items = biblioteca.getItems(Biblioteca.FILTER.AVAILABLE);
        // Then
        boolean isAllItemAvailable = items.stream().allMatch(Rental::isAvailable);
        assertEquals("getItems AVAILABLE should return only available items", true, isAllItemAvailable);
    }

    @Test
    public void shouldGetNotAvailableItems() {
        // Given
        biblioteca.user().login("111-1111", "1111");
        biblioteca.doCheckOut("Book A");
        // When
        ArrayList<Rental> items = biblioteca.getItems(Biblioteca.FILTER.NOT_AVAILABLE);
        // Then
        boolean isAllItemNotAvailable = items.stream().allMatch(item -> !item.isAvailable());
        assertEquals("getItems NOT_AVAILABLE should return only not available items", true, isAllItemNotAvailable);
    }

    @Test
    public void shouldGetAllItems() {
        // Given
        biblioteca.user().login("111-1111", "1111");
        biblioteca.doCheckOut("Book A");
        // When
        ArrayList<Rental> items = biblioteca.getItems(Biblioteca.FILTER.ALL);
        // Then
        boolean isSomeItemAvailable = items.stream().anyMatch(Rental::isAvailable);
        boolean isSomeItemNotAvailable = items.stream().anyMatch(item -> !item.isAvailable());
        boolean isShowAllItem = isSomeItemAvailable & isSomeItemNotAvailable;
        assertEquals("getItems ALL should return both available and not available items", true, isShowAllItem);
    }

    @Test
    public void shouldCheckOutAvailableItem() {
        // Given
        biblioteca.user().login("111-1111", "1111");
        // When
        Biblioteca.RESPONSE response = biblioteca.doCheckOut("Book A");
        // Then
        assertEquals("doCheckOut should response success when checkout available item", Biblioteca.RESPONSE.SUCCESS, response);
    }

    @Test
    public void shouldNotCheckOutNotAvailableItem() {
        // Given
        biblioteca.user().login("111-1111", "1111");
        biblioteca.doCheckOut("Book A");
        // When
        Biblioteca.RESPONSE response = biblioteca.doCheckOut("Book A");
        // Then
        assertEquals("doCheckOut should response default error when checkout non-available item", Biblioteca.RESPONSE.DEFAULT_ERROR, response);
    }

    @Test
    public void shouldNotCheckOutInvalidItem() {
        // Given
        biblioteca.user().login("111-1111", "1111");
        // When
        Biblioteca.RESPONSE response = biblioteca.doCheckOut("Book Z");
        // Then
        assertEquals("doCheckOut should response default error when checkout invalid item", Biblioteca.RESPONSE.DEFAULT_ERROR, response);
    }

    @Test
    public void shouldReturnCorrectItem() {
        // Given
        biblioteca.user().login("111-1111", "1111");
        biblioteca.doCheckOut("Book A");
        biblioteca.doCheckOut("Book B");
        // When
        Biblioteca.RESPONSE response = biblioteca.doReturn("Book A");
        // Then
        assertEquals("doReturn should response success when return correct item", Biblioteca.RESPONSE.SUCCESS, response);
    }

    @Test
    public void shouldNotReturnAvailableItem() {
        // Given
        biblioteca.user().login("111-1111", "1111");
        // When
        Biblioteca.RESPONSE response = biblioteca.doReturn("Book A");
        // Then
        assertEquals("doReturn should response default error when return available item", Biblioteca.RESPONSE.DEFAULT_ERROR, response);
    }

    @Test
    public void shouldNotReturnWrongItem() {
        // Given
        biblioteca.user().login("111-1111", "1111");
        // When
        Biblioteca.RESPONSE response = biblioteca.doReturn("Book Z");
        // Then
        assertEquals("doReturn should response default error when return invalid item", Biblioteca.RESPONSE.DEFAULT_ERROR, response);
    }

    @Test
    public void shouldNotReturnWrongUser() {
        // Given
        biblioteca.user().login("111-1111", "1111");
        biblioteca.doCheckOut("Book A");
        biblioteca.user().login("222-2222", "2222");
        // When
        Biblioteca.RESPONSE response = biblioteca.doReturn("Book A");
        // Then
        assertEquals("doReturn should response authorization error when return item of different user", Biblioteca.RESPONSE.AUTHORIZATION_ERROR, response);
    }
}
