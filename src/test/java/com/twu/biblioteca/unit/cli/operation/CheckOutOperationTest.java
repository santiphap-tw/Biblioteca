package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.operation.CheckOutOperation;
import com.twu.biblioteca.model.Label;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CheckOutOperationTest {

    private CheckOutOperation checkOutOperation;

    @Before
    public void initialize(){
        // Given
        Biblioteca.getInstance().initialize();
        checkOutOperation = new CheckOutOperation("");
        Biblioteca.getInstance().user().login("111-1111", "1111");
    }

    @Test
    public void shouldCheckOutCorrectItem() {
        // Given
        // When
        ArrayList<String> output = checkOutOperation.run("Book A");
        // Then
        boolean isSuccess = output.stream().anyMatch(text -> text.equals(Label.CHECKOUT_SUCCESS.text));
        assertEquals("checkOut should be success", true, isSuccess);
    }

    @Test
    public void shouldNotCheckOutNotAvailableItem() {
        // Given
        checkOutOperation.run("Book A");
        // When
        ArrayList<String> output = checkOutOperation.run("Book A");
        // Then
        boolean isFailWhenNotAvailable = output.stream().anyMatch(text -> text.equals(Label.CHECKOUT_FAIL.text));
        assertEquals("checkOut should be fail", true, isFailWhenNotAvailable);
    }

    @Test
    public void shouldNotCheckOutWrongItem() {
        // Given
        // When
        ArrayList<String> output = checkOutOperation.run("Book Z");
        // Then
        boolean isFailWhenIncorrectName = output.stream().anyMatch(text -> text.equals(Label.CHECKOUT_FAIL.text));
        assertEquals("checkOut should be fail", true, isFailWhenIncorrectName);
    }

    @Test
    public void shouldNotCheckOutUnauthorizedUser() {
        // Given
        Biblioteca.getInstance().user().logout();
        // When
        ArrayList<String> output = checkOutOperation.run("Book B");
        // Then
        boolean isFailWhenNotLoggedIn = output.stream().anyMatch(text -> text.equals(Label.AUTHORIZATION_ERROR.text));
        assertEquals("checkOut should be fail", true, isFailWhenNotLoggedIn);
    }
}
