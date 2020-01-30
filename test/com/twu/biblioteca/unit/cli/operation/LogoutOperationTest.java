package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.operation.LoginOperation;
import com.twu.biblioteca.cli.operation.LogoutOperation;
import com.twu.biblioteca.model.Label;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class LogoutOperationTest {

    private Biblioteca biblioteca;
    private LogoutOperation logoutOperation;

    @Before
    public void initialize(){
        biblioteca = new Biblioteca();
        logoutOperation = new LogoutOperation("", biblioteca);
        biblioteca.user().login("111-1111", "1111");
    }

    @Test
    public void logoutTest() {
        // Setup operation
        ArrayList<String> output;

        // Positive test
        output = logoutOperation.run("");
        boolean isSuccess = output.stream().anyMatch(text -> text.contains(Label.LOGOUT_SUCCESS.text));
        assertEquals("logout should be success", true, isSuccess);

        // Negative test
        output = logoutOperation.run("");
        boolean isFail = output.stream().anyMatch(text -> text.contains(Label.MY_INFO_FAIL.text));
        assertEquals("login should be fail", true, isFail);
    }
}
