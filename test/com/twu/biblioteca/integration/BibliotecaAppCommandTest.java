package com.twu.biblioteca.integration;


import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.*;
import com.twu.biblioteca.cli.BibliotecaApp;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class BibliotecaAppCommandTest {

    @Test(timeout=1000)
    public void appCanHandleInput() {
        // Given
        String input = Label.OPTION_EXIT_COMMAND.text;
        InputStream originalInputStream = System.in;
        ByteArrayInputStream inputContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputContent);
        // When
        BibliotecaApp app = new BibliotecaApp();
        app.start();
        // Then
        // App should exit
        // After
        System.setIn(originalInputStream);
    }

    @Test
    public void appHaveExitCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_EXIT_COMMAND.text);
        // Then
        assertEquals("Exit command should have exit response", BibliotecaApp.RESPONSE.EXIT, response);
    }

    @Test
    public void appHaveShowCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_SHOW_ALL_COMMAND.text);
        // Then
        assertEquals("Show command should be valid", BibliotecaApp.RESPONSE.VALID, response);
    }

    @Test
    public void appHaveShowBookCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_SHOW_BOOKS_COMMAND.text);
        // Then
        assertEquals("Show book command should be valid", BibliotecaApp.RESPONSE.VALID, response);
    }

    @Test
    public void appHaveShowMovieCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_SHOW_MOVIES_COMMAND.text);
        // Then
        assertEquals("Show movie command should be valid", BibliotecaApp.RESPONSE.VALID, response);
    }

    @Test
    public void appHaveCheckOutCommand() {
        // Given
        Biblioteca biblioteca = new Biblioteca();
        BibliotecaApp app = new BibliotecaApp(biblioteca);
        app.selectOption(Label.OPTION_LOGIN_COMMAND.text + " 111-1111 1111");
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_CHECKOUT_COMMAND.text + " Book A");
        // Then
        assertEquals("Check out command should be valid", BibliotecaApp.RESPONSE.VALID, response);
        assertEquals("App should have 5 items after checkout", 5, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
    }

    @Test
    public void appHaveReturnCommand() {
        // Given
        Biblioteca biblioteca = new Biblioteca();
        BibliotecaApp app = new BibliotecaApp(biblioteca);
        app.selectOption(Label.OPTION_LOGIN_COMMAND.text + " 111-1111 1111");
        app.selectOption(Label.OPTION_CHECKOUT_COMMAND.text + " Book A");
        app.selectOption(Label.OPTION_CHECKOUT_COMMAND.text + " Book B");
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_RETURN_COMMAND.text + " Book A");
        // Then
        assertEquals("Return command should be valid", BibliotecaApp.RESPONSE.VALID, response);
        assertEquals("App should have 5 items after return", 5, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
    }

    @Test
    public void appHaveLoginCommand() {
        // Given
        Biblioteca biblioteca = new Biblioteca();
        BibliotecaApp app = new BibliotecaApp(biblioteca);
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_LOGIN_COMMAND.text + " 111-1111 1111");
        // Then
        assertEquals("Login command should be valid", BibliotecaApp.RESPONSE.VALID, response);
        assertEquals("App should have logged in with user 111-1111", "111-1111", biblioteca.user().getCurrentUser().getId());
    }

    @Test
    public void appHaveLogoutCommand() {
        // Given
        Biblioteca biblioteca = new Biblioteca();
        BibliotecaApp app = new BibliotecaApp(biblioteca);
        app.selectOption(Label.OPTION_LOGIN_COMMAND.text + " 111-1111 1111");
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_LOGOUT_COMMAND.text);
        // Then
        assertEquals("Logout command should be valid", BibliotecaApp.RESPONSE.VALID, response);
        assertEquals("App should have logged out", null, biblioteca.user().getCurrentUser());
    }

    @Test
    public void appHaveHelpCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_HELP_COMMAND.text);
        // Then
        assertEquals("Help command should be valid", BibliotecaApp.RESPONSE.VALID, response);
    }

    @Test
    public void appHaveMyInfoCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_MY_INFO_COMMAND.text);
        // Then
        assertEquals("My info command should be valid", BibliotecaApp.RESPONSE.VALID, response);
    }

    @Test
    public void appHaveMyBorrowingCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        // When
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_MY_BORROWING_COMMAND.text);
        // Then
        assertEquals("Borrowing command should be valid", BibliotecaApp.RESPONSE.VALID, response);
    }
}
