package com.twu.biblioteca.unit;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.database.RentalDatabase;
import com.twu.biblioteca.model.Rental;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class BibliotecaTest {


    @Before
    public void initialize() {
        // Given
        Biblioteca.getInstance().initialize();
    }

    @Test
    public void shouldGetAvailableItems() {
        // Given
        Biblioteca.getInstance().user().login("111-1111", "1111");
        Biblioteca.getInstance().doCheckOut("Book A");
        // When
        ArrayList<Rental> items = RentalDatabase.getInstance().getItems(RentalDatabase.Filter.AVAILABLE);
        // Then
        boolean isAllItemAvailable = items.stream().allMatch(Rental::isAvailable);
        assertEquals("getItems AVAILABLE should return only available items", true, isAllItemAvailable);
    }

    @Test
    public void shouldGetNotAvailableItems() {
        // Given
        Biblioteca.getInstance().user().login("111-1111", "1111");
        Biblioteca.getInstance().doCheckOut("Book A");
        // When
        ArrayList<Rental> items = RentalDatabase.getInstance().getItems(RentalDatabase.Filter.NOT_AVAILABLE);
        // Then
        boolean isAllItemNotAvailable = items.stream().allMatch(item -> !item.isAvailable());
        assertEquals("getItems NOT_AVAILABLE should return only not available items", true, isAllItemNotAvailable);
    }

    @Test
    public void shouldGetAllItems() {
        // Given
        Biblioteca.getInstance().user().login("111-1111", "1111");
        Biblioteca.getInstance().doCheckOut("Book A");
        // When
        ArrayList<Rental> items = RentalDatabase.getInstance().getItems(RentalDatabase.Filter.ALL);
        // Then
        boolean isSomeItemAvailable = items.stream().anyMatch(Rental::isAvailable);
        boolean isSomeItemNotAvailable = items.stream().anyMatch(item -> !item.isAvailable());
        boolean isShowAllItem = isSomeItemAvailable & isSomeItemNotAvailable;
        assertEquals("getItems ALL should return both available and not available items", true, isShowAllItem);
    }

    @Test
    public void shouldCheckOutAvailableItem() {
        // Given
        Biblioteca.getInstance().user().login("111-1111", "1111");
        // When
        Biblioteca.RESPONSE response = Biblioteca.getInstance().doCheckOut("Book A");
        // Then
        assertEquals("doCheckOut should response success when checkout available item", Biblioteca.RESPONSE.SUCCESS, response);
    }

    @Test
    public void shouldNotCheckOutNotAvailableItem() {
        // Given
        Biblioteca.getInstance().user().login("111-1111", "1111");
        Biblioteca.getInstance().doCheckOut("Book A");
        // When
        Biblioteca.RESPONSE response = Biblioteca.getInstance().doCheckOut("Book A");
        // Then
        assertEquals("doCheckOut should response default error when checkout non-available item", Biblioteca.RESPONSE.DEFAULT_ERROR, response);
    }

    @Test
    public void shouldNotCheckOutInvalidItem() {
        // Given
        Biblioteca.getInstance().user().login("111-1111", "1111");
        // When
        Biblioteca.RESPONSE response = Biblioteca.getInstance().doCheckOut("Book Z");
        // Then
        assertEquals("doCheckOut should response default error when checkout invalid item", Biblioteca.RESPONSE.DEFAULT_ERROR, response);
    }

    @Test
    public void shouldReturnCorrectItem() {
        // Given
        Biblioteca.getInstance().user().login("111-1111", "1111");
        Biblioteca.getInstance().doCheckOut("Book A");
        Biblioteca.getInstance().doCheckOut("Book B");
        // When
        Biblioteca.RESPONSE response = Biblioteca.getInstance().doReturn("Book A");
        // Then
        assertEquals("doReturn should response success when return correct item", Biblioteca.RESPONSE.SUCCESS, response);
    }

    @Test
    public void shouldNotReturnAvailableItem() {
        // Given
        Biblioteca.getInstance().user().login("111-1111", "1111");
        // When
        Biblioteca.RESPONSE response = Biblioteca.getInstance().doReturn("Book A");
        // Then
        assertEquals("doReturn should response default error when return available item", Biblioteca.RESPONSE.DEFAULT_ERROR, response);
    }

    @Test
    public void shouldNotReturnWrongItem() {
        // Given
        Biblioteca.getInstance().user().login("111-1111", "1111");
        // When
        Biblioteca.RESPONSE response = Biblioteca.getInstance().doReturn("Book Z");
        // Then
        assertEquals("doReturn should response default error when return invalid item", Biblioteca.RESPONSE.DEFAULT_ERROR, response);
    }

    @Test
    public void shouldNotReturnWrongUser() {
        // Given
        Biblioteca.getInstance().user().login("111-1111", "1111");
        Biblioteca.getInstance().doCheckOut("Book A");
        Biblioteca.getInstance().user().login("222-2222", "2222");
        // When
        Biblioteca.RESPONSE response = Biblioteca.getInstance().doReturn("Book A");
        // Then
        assertEquals("doReturn should response authorization error when return item of different user", Biblioteca.RESPONSE.AUTHORIZATION_ERROR, response);
    }
}
