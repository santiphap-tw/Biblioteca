package com.twu.biblioteca;


import com.twu.biblioteca.model.*;
import com.twu.biblioteca.cli.BibliotecaApp;
import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class AppTest {

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
    public void bibliotecaAppHaveExitCommand() {
        simulateInput(Label.OPTION_EXIT_COMMAND.text);
        BibliotecaApp app = new BibliotecaApp();
        app.start();
    }

    @Test(timeout=1000)
    public void bibliotecaAppHaveShowCommand() {
        simulateInput(new String[] {Label.OPTION_SHOW_ALL_COMMAND.text, Label.OPTION_EXIT_COMMAND.text});
        BibliotecaApp app = new BibliotecaApp();
        app.start();
    }

    @Test(timeout=1000)
    public void bibliotecaAppHaveShowBookCommand() {
        simulateInput(new String[] {Label.OPTION_SHOW_BOOKS_COMMAND.text,Label.OPTION_EXIT_COMMAND.text});
        BibliotecaApp app = new BibliotecaApp();
        app.start();
    }

    @Test(timeout=1000)
    public void bibliotecaAppHaveShowMovieCommand() {
        simulateInput(new String[] {Label.OPTION_SHOW_MOVIES_COMMAND.text,Label.OPTION_EXIT_COMMAND.text});
        BibliotecaApp app = new BibliotecaApp();
        app.start();
    }

    @Test(timeout=1000)
    public void bibliotecaAppHaveCheckOutCommand() {
        simulateInput(new String[] {Label.OPTION_CHECKOUT_COMMAND.text + " Book A",Label.OPTION_EXIT_COMMAND.text});
        Biblioteca biblioteca = new Biblioteca();
        BibliotecaApp app = new BibliotecaApp(biblioteca);
        app.start();
        assertEquals("Biblioteca app should have 2 items after checkout", 2, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
    }

    @Test(timeout=1000)
    public void bibliotecaAppHaveReturnCommand() {
        simulateInput(new String[] {Label.OPTION_CHECKOUT_COMMAND.text + " Book A",Label.OPTION_CHECKOUT_COMMAND.text + " Book B",Label.OPTION_RETURN_COMMAND.text + " Book A",Label.OPTION_EXIT_COMMAND.text});
        Biblioteca biblioteca = new Biblioteca();
        BibliotecaApp app = new BibliotecaApp(biblioteca);
        app.start();
        assertEquals("Biblioteca app should have 2 items after return", 2, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
    }

    @Test
    public void bibliotecaShouldHaveCheckOut() {
        Biblioteca biblioteca = new Biblioteca();
        assertEquals("Biblioteca should have 3 books at start", 3, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
        biblioteca.doCheckOut("Book A");
        assertEquals("Biblioteca should have 2 books after checkout", 2, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
        biblioteca.doCheckOut("Book B");
        assertEquals("Biblioteca should have 1 books after checkout", 1, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
    }

    @Test
    public void bibliotecaShouldHaveReturn() {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.doCheckOut("Book A");
        biblioteca.doCheckOut("Book B");
        assertEquals("Biblioteca should have 1 books at start", 1, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
        biblioteca.doReturn("Book A");
        assertEquals("Biblioteca should have 2 books after return", 2, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
    }

    @Test
    public void bookShouldHaveInformation() {
        Book book = new Book("Title","Author","Date");
        assertEquals("Book should have a title", "Title", book.getTitle());
        assertEquals("Book should have an author", "Author", book.getAuthor());
        assertEquals("Book should have a publish date", "Date", book.getPublishDate());
    }

    @Test
    public void bookShouldHaveCheckOutAndReturn() {
        Book book = new Book("Title","Author","Date");
        assertEquals("Book should available", true, book.isAvailable());
        book.doCheckOut();
        assertEquals("Book should not available after check out", false, book.isAvailable());
        book.doReturn();
        assertEquals("Book should available after return", true, book.isAvailable());
    }

    @Test
    public void movieShouldHaveInformation() {
        Movie movie = new Movie("Title",2020,"Director",10);
        assertEquals("Movie should have a title", "Title", movie.getTitle());
        assertEquals("Movie should have a year", 2020, movie.getYear());
        assertEquals("Movie should have a director", "Director", movie.getDirector());
        assertEquals("Movie should have a rating", 10, movie.getRating());
    }

    @Test
    public void MovieShouldHaveCheckOutAndReturn() {
        Movie movie = new Movie("Title",2020,"Director",10);
        assertEquals("Movie should available", true, movie.isAvailable());
        movie.doCheckOut();
        assertEquals("Movie should not available after check out", false, movie.isAvailable());
        movie.doReturn();
        assertEquals("Movie should available after return", true, movie.isAvailable());
    }

    @Test
    public void UserShouldHaveInformation() {
        User user = new User("111-1111", "1234", "Name", "Email", "Phone");
        assertEquals("User should have id", "111-1111", user.getId());
        assertEquals("User should have password", "1234", user.getPassword());
        assertEquals("User should have name", "Name", user.getName());
        assertEquals("User should have email", "Email", user.getEmail());
        assertEquals("User should have phone", "Phone", user.getPhone());
    }

    @Test
    public void UserShouldHaveCheckOutandReturnItems() {
        User user = new User("111-1111", "1234", "Name", "Email", "Phone");
        Book book = new Book("Title","Author","Date");
        Movie movie = new Movie("Title",2020,"Director",10);
        user.doCheckOut(book);
        user.doCheckOut(movie);
        assertEquals("User should have 2 items", 2, user.getItems().size());
        user.doReturn(book);
        user.doReturn(movie);
        assertEquals("User should have no item", 0, user.getItems().size());
    }

    @Test
    public void bibliotecaShouldHaveUserAccess() {
        Biblioteca biblioteca = new Biblioteca();
        assertEquals("Biblioteca should have users list", 3, biblioteca.getUsers().size());
        assertEquals("Biblioteca should have no current user", null, biblioteca.getCurrentUser());
        biblioteca.login("111-1111", "1111");
        assertEquals("Biblioteca should have the current user", "111-1111", biblioteca.getCurrentUser().getId());
        biblioteca.logout();
        assertEquals("Biblioteca should have no current user", null, biblioteca.getCurrentUser());
    }

}
