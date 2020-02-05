package com.twu.biblioteca.unit.cli;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.BibliotecaApp;
import com.twu.biblioteca.database.RentalDatabase;
import com.twu.biblioteca.model.Label;
import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class BibliotecaAppTest {

    @After
    public void revertToOriginalInputOutputStream() {
        System.setIn(System.in);
        System.setOut(System.out);
    }

    @Test(timeout = 1000)
    public void shouldExitAfterInputExitCommand() { // To test if the app can handle input or not
        // Given
        String input = Label.OPTION_EXIT_COMMAND.text;
        ByteArrayInputStream inputContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputContent);
        // When
        BibliotecaApp app = new BibliotecaApp();
        app.start();
        // Then
        // App should finish running
    }

    @Test
    public void shouldHaveExitCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_EXIT_COMMAND.text);
        // Then
        assertEquals("Exit command should have exit response", BibliotecaApp.RESPONSE.EXIT, response);
    }

    @Test
    public void shouldHaveShowCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_SHOW_ALL_COMMAND.text);
        // Then
        assertEquals("Show command should be valid", BibliotecaApp.RESPONSE.VALID, response);
    }

    @Test
    public void shouldHaveShowBookCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_SHOW_BOOKS_COMMAND.text);
        // Then
        assertEquals("Show book command should be valid", BibliotecaApp.RESPONSE.VALID, response);
    }

    @Test
    public void shouldHaveShowMovieCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_SHOW_MOVIES_COMMAND.text);
        // Then
        assertEquals("Show movie command should be valid", BibliotecaApp.RESPONSE.VALID, response);
    }

    @Test
    public void shouldHaveCheckOutCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        app.selectOption(Label.OPTION_LOGIN_COMMAND.text + " 111-1111 1111");
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_CHECKOUT_COMMAND.text + " Book A");
        // Then
        assertEquals("Check out command should be valid", BibliotecaApp.RESPONSE.VALID, response);
        assertEquals("App should have 5 items after checkout", 5, RentalDatabase.getInstance().getItems(RentalDatabase.Filter.AVAILABLE).size());
    }

    @Test
    public void shouldHaveReturnCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        app.selectOption(Label.OPTION_LOGIN_COMMAND.text + " 111-1111 1111");
        app.selectOption(Label.OPTION_CHECKOUT_COMMAND.text + " Book A");
        app.selectOption(Label.OPTION_CHECKOUT_COMMAND.text + " Book B");
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_RETURN_COMMAND.text + " Book A");
        // Then
        assertEquals("Return command should be valid", BibliotecaApp.RESPONSE.VALID, response);
        assertEquals("App should have 5 items after return", 5, RentalDatabase.getInstance().getItems(RentalDatabase.Filter.AVAILABLE).size());
    }

    @Test
    public void shouldHaveLoginCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_LOGIN_COMMAND.text + " 111-1111 1111");
        // Then
        assertEquals("Login command should be valid", BibliotecaApp.RESPONSE.VALID, response);
        assertEquals("App should have logged in with user 111-1111", "111-1111", Biblioteca.getInstance().user().getCurrentUser().getId());
    }

    @Test
    public void shouldHaveLogoutCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        app.selectOption(Label.OPTION_LOGIN_COMMAND.text + " 111-1111 1111");
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_LOGOUT_COMMAND.text);
        // Then
        assertEquals("Logout command should be valid", BibliotecaApp.RESPONSE.VALID, response);
        assertEquals("App should have logged out", null, Biblioteca.getInstance().user().getCurrentUser());
    }

    @Test
    public void shouldHaveHelpCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_HELP_COMMAND.text);
        // Then
        assertEquals("Help command should be valid", BibliotecaApp.RESPONSE.VALID, response);
    }

    @Test
    public void shouldHaveMyInfoCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_MY_INFO_COMMAND.text);
        // Then
        assertEquals("My info command should be valid", BibliotecaApp.RESPONSE.VALID, response);
    }

    @Test
    public void shouldHaveMyBorrowingCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_MY_BORROWING_COMMAND.text);
        // Then
        assertEquals("Borrowing command should be valid", BibliotecaApp.RESPONSE.VALID, response);
    }

    @Test
    public void shouldHavePrintOutputCommand() {
        // Given
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ArrayList<String> output = new ArrayList<String>();
        output.add("test line 1");
        output.add("test line 2");
        // When
        BibliotecaApp.printOutput(output);
        // Then
        assertEquals("printOutput should print same as input", "test line 1\ntest line 2\n", outContent.toString());
    }
}
