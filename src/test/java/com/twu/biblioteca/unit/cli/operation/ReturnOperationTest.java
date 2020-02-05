package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.operation.ReturnOperation;
import com.twu.biblioteca.model.Label;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ReturnOperationTest {

    private Biblioteca biblioteca;
    private ReturnOperation returnOperation;

    @Before
    public void initialize(){
        // Given
        biblioteca = new Biblioteca();
        returnOperation = new ReturnOperation("", biblioteca);
        biblioteca.user().login("111-1111", "1111");
        biblioteca.doCheckOut("Book A");
        biblioteca.doCheckOut("Movie A");
    }

    @Test
    public void shouldReturnCorrectItem() {
        // Given
        // When
        ArrayList<String> output = returnOperation.run("Book A");
        // Then
        boolean isSuccess = output.stream().anyMatch(text -> text.equals(Label.RETURN_SUCCESS.text));
        assertEquals("return should be success", true, isSuccess);
    }

    @Test
    public void shouldNotReturnAvailableItem() {
        // Given
        returnOperation.run("Book A");
        // When
        ArrayList<String> output = returnOperation.run("Book A");
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
        biblioteca.user().logout();
        // When
        ArrayList<String> output = returnOperation.run("Movie A");
        // Then
        boolean isFailWhenNotLoggedIn = output.stream().anyMatch(text -> text.equals(Label.AUTHORIZATION_ERROR.text));
        assertEquals("return should be fail", true, isFailWhenNotLoggedIn);
    }

    @Test
    public void shouldNotReturnWrongUser() {
        // Given
        biblioteca.user().login("222-2222", "2222");
        // When
        ArrayList<String> output = returnOperation.run("Movie A");
        // Then
        boolean isFailWhenWrongUser = output.stream().anyMatch(text -> text.equals(Label.AUTHORIZATION_ERROR.text));
        assertEquals("return should be fail", true, isFailWhenWrongUser);
    }
}
