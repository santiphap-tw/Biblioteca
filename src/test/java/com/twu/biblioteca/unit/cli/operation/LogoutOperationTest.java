package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.operation.LogoutOperation;
import com.twu.biblioteca.database.UserDatabase;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class LogoutOperationTest {

    private User sampleUser1 = UserDatabase.getInstance().getUsers().get(0);

    private LogoutOperation logoutOperation;

    @Before
    public void initialize(){
        // Given
        logoutOperation = new LogoutOperation("");
        Biblioteca.getInstance().user().login(sampleUser1.getId(), sampleUser1.getPassword());
    }

    @Test
    public void shouldLogoutCurrentUser() {
        // Given
        // When
        ArrayList<String> output = logoutOperation.run("");
        // Then
        boolean isSuccess = output.stream().anyMatch(text -> text.contains(Label.LOGOUT_SUCCESS.text));
        assertEquals("logout should be success", true, isSuccess);
    }

    @Test
    public void shouldNotLogoutIfNoUser() {
        // Given
        logoutOperation.run("");
        // When
        ArrayList<String> output = logoutOperation.run("");
        // Then
        boolean isFail = output.stream().anyMatch(text -> text.contains(Label.MY_INFO_FAIL.text));
        assertEquals("login should be fail", true, isFail);
    }
}
