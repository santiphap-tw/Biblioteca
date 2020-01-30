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
        String input = Label.OPTION_EXIT_COMMAND.text;
        InputStream originalInputStream = System.in;
        ByteArrayInputStream inputContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputContent);
        BibliotecaApp app = new BibliotecaApp();
        app.start();
        System.setIn(originalInputStream);
    }

    @Test
    public void appHaveExitCommand() {
        BibliotecaApp app = new BibliotecaApp();
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_EXIT_COMMAND.text);
        assertEquals("Exit command should have exit response", BibliotecaApp.RESPONSE.EXIT, response);
    }

    @Test
    public void appHaveShowCommand() {
        BibliotecaApp app = new BibliotecaApp();
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_SHOW_ALL_COMMAND.text);
        assertEquals("Show command should be valid", BibliotecaApp.RESPONSE.VALID, response);
    }

    @Test
    public void appHaveShowBookCommand() {
        BibliotecaApp app = new BibliotecaApp();
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_SHOW_BOOKS_COMMAND.text);
        assertEquals("Show book command should be valid", BibliotecaApp.RESPONSE.VALID, response);
    }

    @Test
    public void appHaveShowMovieCommand() {
        BibliotecaApp app = new BibliotecaApp();
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_SHOW_MOVIES_COMMAND.text);
        assertEquals("Show movie command should be valid", BibliotecaApp.RESPONSE.VALID, response);
    }

    @Test
    public void appHaveCheckOutCommand() {
        Biblioteca biblioteca = new Biblioteca();
        BibliotecaApp app = new BibliotecaApp(biblioteca);
        app.selectOption(Label.OPTION_LOGIN_COMMAND.text + " 111-1111 1111");
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_CHECKOUT_COMMAND.text + " Book A");
        assertEquals("Check out command should be valid", BibliotecaApp.RESPONSE.VALID, response);
        assertEquals("App should have 5 items after checkout", 5, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
    }

    @Test
    public void appHaveReturnCommand() {
        Biblioteca biblioteca = new Biblioteca();
        BibliotecaApp app = new BibliotecaApp(biblioteca);
        app.selectOption(Label.OPTION_LOGIN_COMMAND.text + " 111-1111 1111");
        app.selectOption(Label.OPTION_CHECKOUT_COMMAND.text + " Book A");
        app.selectOption(Label.OPTION_CHECKOUT_COMMAND.text + " Book B");
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_RETURN_COMMAND.text + " Book A");
        assertEquals("Return command should be valid", BibliotecaApp.RESPONSE.VALID, response);
        assertEquals("App should have 5 items after return", 5, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
    }

    @Test
    public void appHaveLoginCommand() {
        Biblioteca biblioteca = new Biblioteca();
        BibliotecaApp app = new BibliotecaApp(biblioteca);
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_LOGIN_COMMAND.text + " 111-1111 1111");
        assertEquals("Login command should be valid", BibliotecaApp.RESPONSE.VALID, response);
        assertEquals("App should have logged in with user 111-1111", "111-1111", biblioteca.user().getCurrentUser().getId());
    }

    @Test
    public void appHaveLogoutCommand() {
        Biblioteca biblioteca = new Biblioteca();
        BibliotecaApp app = new BibliotecaApp(biblioteca);
        app.selectOption(Label.OPTION_LOGIN_COMMAND.text + " 111-1111 1111");
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_LOGOUT_COMMAND.text);
        assertEquals("Logout command should be valid", BibliotecaApp.RESPONSE.VALID, response);
        assertEquals("App should have logged out", null, biblioteca.user().getCurrentUser());
    }

    @Test
    public void appHaveHelpCommand() {
        BibliotecaApp app = new BibliotecaApp();
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_HELP_COMMAND.text);
        assertEquals("Help command should be valid", BibliotecaApp.RESPONSE.VALID, response);
    }

    @Test
    public void appHaveMyInfoCommand() {
        BibliotecaApp app = new BibliotecaApp();
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_MY_INFO_COMMAND.text);
        assertEquals("My info command should be valid", BibliotecaApp.RESPONSE.VALID, response);
    }

    @Test
    public void appHaveMyBorrowingCommand() {
        BibliotecaApp app = new BibliotecaApp();
        BibliotecaApp.RESPONSE response = app.selectOption(Label.OPTION_MY_BORROWING_COMMAND.text);
        assertEquals("Borrowing command should be valid", BibliotecaApp.RESPONSE.VALID, response);
    }
}
