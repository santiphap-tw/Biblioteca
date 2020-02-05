package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.operation.ReturnOperation;
import com.twu.biblioteca.database.RentalDatabase;
import com.twu.biblioteca.database.UserDatabase;
import com.twu.biblioteca.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ReturnOperationTest {

    private User sampleUser1 = UserDatabase.getInstance().getUsers().get(0);
    private User sampleUser2 = UserDatabase.getInstance().getUsers().get(1);
    private Rental sampleItem1 = RentalDatabase.getInstance().getItems(item -> item.getClass() == Book.class).get(0);
    private Rental sampleItem2 = RentalDatabase.getInstance().getItems(item -> item.getClass() == Movie.class).get(0);

    private ReturnOperation returnOperation;

    @Before
    public void initialize(){
        // Given
        Biblioteca.getInstance().initialize();
        returnOperation = new ReturnOperation("");
        Biblioteca.getInstance().user().login(sampleUser1.getId(), sampleUser1.getPassword());
        Biblioteca.getInstance().doCheckOut(sampleItem1.getTitle());
        Biblioteca.getInstance().doCheckOut(sampleItem2.getTitle());
    }

    @Test
    public void shouldReturnCorrectItem() {
        // Given
        // When
        ArrayList<String> output = returnOperation.run(sampleItem1.getTitle());
        // Then
        boolean isSuccess = output.stream().anyMatch(text -> text.equals(Label.RETURN_SUCCESS.text));
        assertEquals("return should be success", true, isSuccess);
    }

    @Test
    public void shouldNotReturnAvailableItem() {
        // Given
        returnOperation.run(sampleItem1.getTitle());
        // When
        ArrayList<String> output = returnOperation.run(sampleItem1.getTitle());
        // Then
        boolean isFailWhenNotAvailable = output.stream().anyMatch(text -> text.equals(Label.RETURN_FAIL.text));
        assertEquals("return should be fail", true, isFailWhenNotAvailable);
    }

    @Test
    public void shouldNotReturnWrongItem() {
        // Given
        // When
        ArrayList<String> output = returnOperation.run("Book Z");
        // Then
        boolean isFailWhenIncorrectName = output.stream().anyMatch(text -> text.equals(Label.RETURN_FAIL.text));
        assertEquals("return should be fail", true, isFailWhenIncorrectName);
    }

    @Test
    public void shouldNotReturnNoUer() {
        // Given
        Biblioteca.getInstance().user().logout();
        // When
        ArrayList<String> output = returnOperation.run(sampleItem2.getTitle());
        // Then
        boolean isFailWhenNotLoggedIn = output.stream().anyMatch(text -> text.equals(Label.AUTHORIZATION_ERROR.text));
        assertEquals("return should be fail", true, isFailWhenNotLoggedIn);
    }

    @Test
    public void shouldNotReturnWrongUser() {
        // Given
        Biblioteca.getInstance().user().login(sampleUser2.getId(), sampleUser2.getPassword());
        // When
        ArrayList<String> output = returnOperation.run(sampleItem2.getTitle());
        // Then
        boolean isFailWhenWrongUser = output.stream().anyMatch(text -> text.equals(Label.AUTHORIZATION_ERROR.text));
        assertEquals("return should be fail", true, isFailWhenWrongUser);
    }
}
