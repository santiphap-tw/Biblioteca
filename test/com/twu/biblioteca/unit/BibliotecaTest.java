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
        biblioteca = new Biblioteca();
    }

    @Test
    public void getItemsTest() {

        // Initial Operation for testing
        biblioteca.user().login("111-1111", "1111");
        biblioteca.doCheckOut("Book A");
        ArrayList<Rental> items = new ArrayList<Rental>();

        // Available Test
        items = biblioteca.getItems(Biblioteca.FILTER.AVAILABLE);
        boolean isAllItemAvailable = items.stream().allMatch(item -> item.isAvailable() == true);
        assertEquals("getItems AVAILABLE should return only available items", true, isAllItemAvailable);

        // Not Available Test
        items = biblioteca.getItems(Biblioteca.FILTER.NOT_AVAILABLE);
        boolean isAllItemNotAvailable = items.stream().allMatch(item -> item.isAvailable() == false);
        assertEquals("getItems NOT_AVAILABLE should return only not available items", true, isAllItemNotAvailable);

        // All Test
        items = biblioteca.getItems(Biblioteca.FILTER.ALL);
        boolean isSomeItemAvailable = items.stream().anyMatch(item -> item.isAvailable() == true);
        boolean isSomeItemNotAvailable = items.stream().anyMatch(item -> !item.isAvailable() == false);
        boolean isShowAllItem = isSomeItemAvailable & isSomeItemNotAvailable;
        assertEquals("getItems ALL should return both available and not available items", true, isShowAllItem);
    }

    @Test
    public void doCheckOutTest() {
        // Initial Operation for testing
        biblioteca.user().login("111-1111", "1111");

        //Positive Testing
        Biblioteca.RESPONSE response = biblioteca.doCheckOut("Book A");
        assertEquals("doCheckOut should response success when checkout available item", Biblioteca.RESPONSE.SUCCESS, response);

        //Negative Testing
        response = biblioteca.doCheckOut("Book A");
        assertEquals("doCheckOut should response default error when checkout non-available item", Biblioteca.RESPONSE.DEFAULT_ERROR, response);
        response = biblioteca.doCheckOut("Book Z");
        assertEquals("doCheckOut should response default error when checkout invalid item", Biblioteca.RESPONSE.DEFAULT_ERROR, response);
    }

    @Test
    public void doReturnTest() {
        // Initial Operation for testing
        biblioteca.user().login("111-1111", "1111");
        biblioteca.doCheckOut("Book A");
        biblioteca.doCheckOut("Book B");

        //Positive Testing
        Biblioteca.RESPONSE response = biblioteca.doReturn("Book A");
        assertEquals("doReturn should response success when return correct item", Biblioteca.RESPONSE.SUCCESS, response);


        //Negative Testing - Wrong item
        response = biblioteca.doReturn("Book A");
        assertEquals("doReturn should response default error when return available item", Biblioteca.RESPONSE.DEFAULT_ERROR, response);
        response = biblioteca.doReturn("Book Z");
        assertEquals("doReturn should response default error when return invalid item", Biblioteca.RESPONSE.DEFAULT_ERROR, response);

        //Negative Testing - Wrong user
        biblioteca.user().login("222-2222", "2222");
        response = biblioteca.doReturn("Book B");
        assertEquals("doReturn should response authorization error when return item of different user", Biblioteca.RESPONSE.AUTHORIZATION_ERROR, response);
    }

    @Test
    public void userTest() {
        // No logic in user(), just return userManager object
        // userManager object will be tested in BibliotecaUserTest
    }
}
