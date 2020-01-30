package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.operation.CheckOutOperation;
import com.twu.biblioteca.model.Label;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CheckOutOperationTest {

    private Biblioteca biblioteca;
    private CheckOutOperation checkOutOperation;

    @Before
    public void initialize(){
        // Given
        biblioteca = new Biblioteca();
        checkOutOperation = new CheckOutOperation("", biblioteca);
        biblioteca.user().login("111-1111", "1111");
    }

    @Test
    public void checkOutTest() {
        ArrayList<String> output;

        // Positive test
        // When
        output = checkOutOperation.run("Book A");
        // Then
        boolean isSuccess = output.stream().anyMatch(text -> text.equals(Label.CHECKOUT_SUCCESS.text));
        assertEquals("checkOut should be success", true, isSuccess);

        // Negative test - Not available item
        // When
        output = checkOutOperation.run("Book A");
        // Then
        boolean isFailWhenNotAvailable = output.stream().anyMatch(text -> text.equals(Label.CHECKOUT_FAIL.text));
        assertEquals("checkOut should be fail", true, isFailWhenNotAvailable);
        // When
        output = checkOutOperation.run("Book Z");
        // Then
        boolean isFailWhenIncorrectName = output.stream().anyMatch(text -> text.equals(Label.CHECKOUT_FAIL.text));
        assertEquals("checkOut should be fail", true, isFailWhenIncorrectName);

        // Negative test - Authorization Error
        // Given
        biblioteca.user().logout();
        // When
        output = checkOutOperation.run("Book B");
        // Then
        boolean isFailWhenNotLoggedIn = output.stream().anyMatch(text -> text.equals(Label.AUTHORIZATION_ERROR.text));
        assertEquals("checkOut should be fail", true, isFailWhenNotLoggedIn);
    }
}
