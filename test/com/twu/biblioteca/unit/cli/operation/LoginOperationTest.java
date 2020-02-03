package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.operation.LoginOperation;
import com.twu.biblioteca.model.Label;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class LoginOperationTest {

    private Biblioteca biblioteca;
    private LoginOperation loginOperation;

    @Before
    public void initialize(){
        // Given
        biblioteca = new Biblioteca();
        loginOperation = new LoginOperation("", biblioteca);
    }

    @Test
    public void shouldLoginCorrectUser() {
        // Given
        // When
        ArrayList<String> output = loginOperation.run("111-1111 1111");
        // Then
        boolean isSuccess = output.stream().anyMatch(text -> text.contains(Label.LOGIN_SUCCESS.text));
        assertEquals("login should be success", true, isSuccess);
    }

    @Test
    public void shouldNotLoginWrongUser() {
        // Given
        // When
        ArrayList<String> output = loginOperation.run("111-1111 0000");
        // Then
        boolean isFail = output.stream().anyMatch(text -> text.contains(Label.LOGIN_FAIL.text));
        assertEquals("login should be fail", true, isFail);
    }
}
