package com.twu.biblioteca.unit.cli;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.BibliotecaApp;
import com.twu.biblioteca.cli.operation.*;
import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.Movie;
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
        ArrayList<String> response = app.runCommand(Label.OPTION_EXIT_COMMAND.text);
        // Then
        ArrayList<String> expectedResponse = (new ExitOperation("")).run("");
        assertEquals("Exit command should have exit response", expectedResponse, response);
    }

    @Test
    public void shouldHaveShowCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        // When
        ArrayList<String> response = app.runCommand(Label.OPTION_SHOW_ALL_COMMAND.text);
        // Then
        ArrayList<String> expectedResponse = (new ShowOperation("")).run("");
        assertEquals("Show command should be show operation", expectedResponse, response);
    }

    @Test
    public void shouldHaveShowBookCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        // When
        ArrayList<String> response = app.runCommand(Label.OPTION_SHOW_BOOKS_COMMAND.text);
        // Then
        ArrayList<String> expectedResponse = (new ShowOperation("", Book.class)).run("");
        assertEquals("Show book command should be show book operation", expectedResponse, response);
    }

    @Test
    public void shouldHaveShowMovieCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        // When
        ArrayList<String> response = app.runCommand(Label.OPTION_SHOW_MOVIES_COMMAND.text);
        // Then
        ArrayList<String> expectedResponse = (new ShowOperation("", Movie.class)).run("");
        assertEquals("Show movie command should be show movie operation", expectedResponse, response);
    }

    @Test
    public void shouldHaveCheckOutCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        app.runCommand(Label.OPTION_LOGIN_COMMAND.text + " 111-1111 1111");
        // When
        ArrayList<String> response = app.runCommand(Label.OPTION_CHECKOUT_COMMAND.text + " Book A");
        // Then
        Biblioteca.getInstance().doReturn("Book A");
        ArrayList<String> expectedResponse = (new CheckOutOperation("")).run("Book A");
        assertEquals("Check out command should be checkout operation", expectedResponse, response);
    }

    @Test
    public void shouldHaveReturnCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        app.runCommand(Label.OPTION_LOGIN_COMMAND.text + " 111-1111 1111");
        app.runCommand(Label.OPTION_CHECKOUT_COMMAND.text + " Book A");
        app.runCommand(Label.OPTION_CHECKOUT_COMMAND.text + " Book B");
        // When
        ArrayList<String> response = app.runCommand(Label.OPTION_RETURN_COMMAND.text + " Book A");
        // Then
        Biblioteca.getInstance().doCheckOut("Book A");
        ArrayList<String> expectedResponse = (new ReturnOperation("")).run("Book A");
        assertEquals("Return command should be return operation", expectedResponse, response);
    }

    @Test
    public void shouldHaveLoginCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        // When
        ArrayList<String> response = app.runCommand(Label.OPTION_LOGIN_COMMAND.text + " 111-1111 1111");
        // Then
        Biblioteca.getInstance().user().logout();
        ArrayList<String> expectedResponse = (new LoginOperation("")).run("111-1111 1111");
        assertEquals("Login command should be login operation", expectedResponse, response);
    }

    @Test
    public void shouldHaveLogoutCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        Biblioteca.getInstance().user().login("111-1111", "1111");
        // When
        ArrayList<String> response = app.runCommand(Label.OPTION_LOGOUT_COMMAND.text);
        // Then
        Biblioteca.getInstance().user().login("111-1111", "1111");
        ArrayList<String> expectedResponse = (new LogoutOperation("")).run("");
        assertEquals("Logout command should be logout operation", expectedResponse, response);
    }

    @Test
    public void shouldHaveHelpCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        // When
        ArrayList<String> response = app.runCommand(Label.OPTION_HELP_COMMAND.text);
        // Then
        ArrayList<String> expectedResponse = (new StartOperation("", null)).run("");
        assertEquals("Help command should be start operation", expectedResponse.get(0), response.get(0));
    }

    @Test
    public void shouldHaveMyInfoCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        Biblioteca.getInstance().user().login("111-1111", "1111");
        // When
        ArrayList<String> response = app.runCommand(Label.OPTION_MY_INFO_COMMAND.text);
        // Then
        ArrayList<String> expectedResponse = (new MyInfoOperation("")).run("");
        assertEquals("My info command should be myinfo operation", expectedResponse, response);
    }

    @Test
    public void shouldHaveMyBorrowingCommand() {
        // Given
        BibliotecaApp app = new BibliotecaApp();
        Biblioteca.getInstance().user().login("111-1111", "1111");
        Biblioteca.getInstance().doCheckOut("Book A");
        Biblioteca.getInstance().doCheckOut("Movie A");
        // When
        ArrayList<String> response = app.runCommand(Label.OPTION_MY_BORROWING_COMMAND.text);
        // Then
        ArrayList<String> expectedResponse = (new MyBorrowOperation("")).run("");
        assertEquals("Borrowing command should be myborrow operation", expectedResponse, response);
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
