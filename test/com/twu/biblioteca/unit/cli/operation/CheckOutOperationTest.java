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
        biblioteca = new Biblioteca();
        checkOutOperation = new CheckOutOperation("", biblioteca);
        biblioteca.user().login("111-1111", "1111");
    }

    @Test
    public void checkOutTest() {
        // Setup operation
        ArrayList<String> output;

        // Positive test
        output = checkOutOperation.run("Book A");
        boolean isSuccess = output.stream().anyMatch(text -> text.equals(Label.CHECKOUT_SUCCESS.text));
        assertEquals("checkOut should be success", true, isSuccess);

        // Negative test - Not available item
        output = checkOutOperation.run("Book A");
        boolean isFailWhenNotAvailable = output.stream().anyMatch(text -> text.equals(Label.CHECKOUT_FAIL.text));
        assertEquals("checkOut should be fail", true, isFailWhenNotAvailable);
        output = checkOutOperation.run("Book Z");
        boolean isFailWhenIncorrectName = output.stream().anyMatch(text -> text.equals(Label.CHECKOUT_FAIL.text));
        assertEquals("checkOut should be fail", true, isFailWhenIncorrectName);

        // Negative test - Authorization Error
        biblioteca.user().logout();
        output = checkOutOperation.run("Book B");
        boolean isFailWhenNotLoggedIn = output.stream().anyMatch(text -> text.equals(Label.AUTHORIZATION_ERROR.text));
        assertEquals("checkOut should be fail", true, isFailWhenNotLoggedIn);
    }
}
