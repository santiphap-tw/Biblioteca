package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.operation.CheckOutOperation;
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
        biblioteca = new Biblioteca();
        loginOperation = new LoginOperation("", biblioteca);
    }

    @Test
    public void loginTest() {
        // Setup operation
        ArrayList<String> output;

        // Positive test
        output = loginOperation.run("111-1111 1111");
        boolean isSuccess = output.stream().anyMatch(text -> text.contains(Label.LOGIN_SUCCESS.text));
        assertEquals("login should be success", true, isSuccess);

        // Negative test
        output = loginOperation.run("111-1111 0000");
        boolean isFail = output.stream().anyMatch(text -> text.contains(Label.LOGIN_FAIL.text));
        assertEquals("login should be fail", true, isFail);
    }
}
