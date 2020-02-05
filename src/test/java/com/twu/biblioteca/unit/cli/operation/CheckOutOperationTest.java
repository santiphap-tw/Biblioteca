package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.operation.CheckOutOperation;
import com.twu.biblioteca.database.RentalDatabase;
import com.twu.biblioteca.database.UserDatabase;
import com.twu.biblioteca.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CheckOutOperationTest {

    private User sampleUser1 = UserDatabase.getInstance().getUsers().get(0);
    private Rental sampleItem1 = RentalDatabase.getInstance().getItems(item -> item.getClass() == Book.class).get(0);
    private Rental sampleItem2 = RentalDatabase.getInstance().getItems(item -> item.getClass() == Movie.class).get(0);

    private CheckOutOperation checkOutOperation;

    @Before
    public void initialize(){
        // Given
        Biblioteca.getInstance().initialize();
        checkOutOperation = new CheckOutOperation("");
        Biblioteca.getInstance().user().login(sampleUser1.getId(), sampleUser1.getPassword());
    }

    @Test
    public void shouldCheckOutCorrectItem() {
        // Given
        // When
        ArrayList<String> output = checkOutOperation.run(sampleItem1.getTitle());
        // Then
        boolean isSuccess = output.stream().anyMatch(text -> text.equals(Label.CHECKOUT_SUCCESS.text));
        assertEquals("checkOut should be success", true, isSuccess);
    }

    @Test
    public void shouldNotCheckOutNotAvailableItem() {
        // Given
        checkOutOperation.run(sampleItem1.getTitle());
        // When
        ArrayList<String> output = checkOutOperation.run(sampleItem1.getTitle());
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
        ArrayList<String> output = checkOutOperation.run(sampleItem2.getTitle());
        // Then
        boolean isFailWhenNotLoggedIn = output.stream().anyMatch(text -> text.equals(Label.AUTHORIZATION_ERROR.text));
        assertEquals("checkOut should be fail", true, isFailWhenNotLoggedIn);
    }
}
