package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.cli.operation.LoginOperation;
import com.twu.biblioteca.database.UserDatabase;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class LoginOperationTest {

    private User sampleUser1 = UserDatabase.getInstance().getUsers().get(0);

    private LoginOperation loginOperation;

    @Before
    public void initialize(){
        loginOperation = new LoginOperation("");
    }

    @Test
    public void shouldLoginCorrectUser() {
        // Given
        // When
        ArrayList<String> output = loginOperation.run(sampleUser1.getId() + " " + sampleUser1.getPassword());
        // Then
        boolean isSuccess = output.stream().anyMatch(text -> text.contains(Label.LOGIN_SUCCESS.text));
        assertEquals("login should be success", true, isSuccess);
    }

    @Test
    public void shouldNotLoginWrongUser() {
        // Given
        // When
        ArrayList<String> output = loginOperation.run(sampleUser1.getId() + " xx" + sampleUser1.getPassword());
        // Then
        boolean isFail = output.stream().anyMatch(text -> text.contains(Label.LOGIN_FAIL.text));
        assertEquals("login should be fail", true, isFail);
    }
}
