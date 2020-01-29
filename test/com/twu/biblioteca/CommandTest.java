package com.twu.biblioteca;


import com.twu.biblioteca.model.*;
import com.twu.biblioteca.cli.BibliotecaApp;
import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class CommandTest {

    private final InputStream originalIn = System.in;

    private void simulateInput(String input) {
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inContent);
    }

    private void simulateInput(String[] inputs) {
        String input = String.join(System.getProperty("line.separator"), inputs);
        simulateInput(input);
    }

    @After
    public void stopSimulateInput() {
        System.setIn(originalIn);
    }

    @Test(timeout=1000)
    public void appHaveExitCommand() {
        simulateInput(Label.OPTION_EXIT_COMMAND.text);
        BibliotecaApp app = new BibliotecaApp();
        app.start();
    }

    @Test(timeout=1000)
    public void appHaveShowCommand() {
        simulateInput(new String[] {Label.OPTION_SHOW_ALL_COMMAND.text, Label.OPTION_EXIT_COMMAND.text});
        BibliotecaApp app = new BibliotecaApp();
        app.start();
    }

    @Test(timeout=1000)
    public void appHaveShowBookCommand() {
        simulateInput(new String[] {Label.OPTION_SHOW_BOOKS_COMMAND.text,Label.OPTION_EXIT_COMMAND.text});
        BibliotecaApp app = new BibliotecaApp();
        app.start();
    }

    @Test(timeout=1000)
    public void appHaveShowMovieCommand() {
        simulateInput(new String[] {Label.OPTION_SHOW_MOVIES_COMMAND.text,Label.OPTION_EXIT_COMMAND.text});
        BibliotecaApp app = new BibliotecaApp();
        app.start();
    }

    @Test(timeout=1000)
    public void appHaveCheckOutCommand() {
        simulateInput(new String[] {Label.OPTION_LOGIN_COMMAND.text + " 111-1111 1111", Label.OPTION_CHECKOUT_COMMAND.text + " Book A",Label.OPTION_EXIT_COMMAND.text});
        Biblioteca biblioteca = new Biblioteca();
        BibliotecaApp app = new BibliotecaApp(biblioteca);
        app.start();
        assertEquals("App should have 5 items after checkout", 5, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
    }

    @Test(timeout=1000)
    public void appHaveReturnCommand() {
        simulateInput(new String[] {Label.OPTION_LOGIN_COMMAND.text + " 111-1111 1111", Label.OPTION_CHECKOUT_COMMAND.text + " Book A",Label.OPTION_CHECKOUT_COMMAND.text + " Book B",Label.OPTION_RETURN_COMMAND.text + " Book A",Label.OPTION_EXIT_COMMAND.text});
        Biblioteca biblioteca = new Biblioteca();
        BibliotecaApp app = new BibliotecaApp(biblioteca);
        app.start();
        assertEquals("App should have 5 items after return", 5, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
    }

    @Test(timeout=1000)
    public void appHaveLoginCommand() {
        simulateInput(new String[] {Label.OPTION_LOGIN_COMMAND.text + " 111-1111 1111", Label.OPTION_EXIT_COMMAND.text});
        Biblioteca biblioteca = new Biblioteca();
        BibliotecaApp app = new BibliotecaApp(biblioteca);
        app.start();
        assertEquals("App should have logged in with user 111-1111", "111-1111", biblioteca.user().getCurrentUser().getId());
    }

    @Test(timeout=1000)
    public void appHaveLogoutCommand() {
        simulateInput(new String[] {Label.OPTION_LOGIN_COMMAND.text + " 111-1111 1111", Label.OPTION_LOGOUT_COMMAND.text, Label.OPTION_EXIT_COMMAND.text});
        Biblioteca biblioteca = new Biblioteca();
        BibliotecaApp app = new BibliotecaApp(biblioteca);
        app.start();
        assertEquals("App should have logged out", null, biblioteca.user().getCurrentUser());
    }

    @Test(timeout=1000)
    public void appHaveHelpCommand() {
        simulateInput(new String[] {Label.OPTION_HELP_COMMAND.text, Label.OPTION_EXIT_COMMAND.text});
        BibliotecaApp app = new BibliotecaApp();
        app.start();
    }

    @Test(timeout=1000)
    public void appHaveMyInfoCommand() {
        simulateInput(new String[] {Label.OPTION_MY_INFO_COMMAND.text, Label.OPTION_EXIT_COMMAND.text});
        BibliotecaApp app = new BibliotecaApp();
        app.start();
    }

    @Test(timeout=1000)
    public void appHaveMyBorrowingCommand() {
        simulateInput(new String[] {Label.OPTION_MY_BORROWING_COMMAND.text, Label.OPTION_EXIT_COMMAND.text});
        BibliotecaApp app = new BibliotecaApp();
        app.start();
    }
}
