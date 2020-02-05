package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.operation.MyInfoOperation;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MyInfoOperationTest {

    private MyInfoOperation myInfoOperation;
    private ArrayList<String> expectedOutput;

    @Before
    public void initialize(){
        // Given
        Biblioteca.getInstance().initialize();
        myInfoOperation = new MyInfoOperation("");
        Biblioteca.getInstance().user().login("111-1111", "1111");
        User currentUser = Biblioteca.getInstance().user().getCurrentUser();
        expectedOutput = new ArrayList<String>();
        expectedOutput.add("ID: \t" + currentUser.getId());
        expectedOutput.add("Name: \t" + currentUser.getName());
        expectedOutput.add("Email: \t" + currentUser.getEmail());
        expectedOutput.add("Phone: \t" + currentUser.getPhone());
    }

    @Test
    public void shouldShowMyInfoIfLoggedIn() {
        // Given
        // When
        ArrayList<String> output = myInfoOperation.run("");
        // Then
        boolean isSuccess = output.equals(expectedOutput);
        assertEquals("my info should be success", true, isSuccess);
    }

    @Test
    public void shouldNotShowMyInfoIfNotLoggedIn() {
        // Given
        Biblioteca.getInstance().user().logout();
        // When
        ArrayList<String> output = myInfoOperation.run("");
        // Then
        boolean isFail = output.stream().anyMatch(text -> text.equals(Label.MY_INFO_FAIL.text));
        assertEquals("my info should be fail", true, isFail);
    }
}
