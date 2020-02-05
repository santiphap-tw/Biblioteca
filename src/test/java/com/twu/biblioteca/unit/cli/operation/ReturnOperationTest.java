package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.operation.ReturnOperation;
import com.twu.biblioteca.model.Label;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ReturnOperationTest {

    private ReturnOperation returnOperation;

    @Before
    public void initialize(){
        // Given
        Biblioteca.getInstance().initialize();
        returnOperation = new ReturnOperation("");
        Biblioteca.getInstance().user().login("111-1111", "1111");
        Biblioteca.getInstance().doCheckOut("Book A");
        Biblioteca.getInstance().doCheckOut("Movie A");
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
        Biblioteca.getInstance().user().logout();
        // When
        ArrayList<String> output = returnOperation.run("Movie A");
        // Then
        boolean isFailWhenNotLoggedIn = output.stream().anyMatch(text -> text.equals(Label.AUTHORIZATION_ERROR.text));
        assertEquals("return should be fail", true, isFailWhenNotLoggedIn);
    }

    @Test
    public void shouldNotReturnWrongUser() {
        // Given
        Biblioteca.getInstance().user().login("222-2222", "2222");
        // When
        ArrayList<String> output = returnOperation.run("Movie A");
        // Then
        boolean isFailWhenWrongUser = output.stream().anyMatch(text -> text.equals(Label.AUTHORIZATION_ERROR.text));
        assertEquals("return should be fail", true, isFailWhenWrongUser);
    }
}
