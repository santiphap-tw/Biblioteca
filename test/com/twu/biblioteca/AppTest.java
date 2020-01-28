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
        assertEquals("App should have logged in with user 111-1111", "111-1111", biblioteca.getCurrentUser().getId());
    }

    @Test(timeout=1000)
    public void appHaveLogoutCommand() {
        simulateInput(new String[] {Label.OPTION_LOGIN_COMMAND.text + " 111-1111 1111", Label.OPTION_LOGOUT_COMMAND.text, Label.OPTION_EXIT_COMMAND.text});
        Biblioteca biblioteca = new Biblioteca();
        BibliotecaApp app = new BibliotecaApp(biblioteca);
        app.start();
        assertEquals("App should have logged out", null, biblioteca.getCurrentUser());
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

    @Test
    public void bibliotecaShouldHaveCheckOut() {
        Biblioteca biblioteca = new Biblioteca();
        assertEquals("Biblioteca should have 6 items at start", 6, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
        biblioteca.doCheckOut("Book A");
        assertEquals("Biblioteca should have 6 items after checkout without user", 6, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
        biblioteca.login("111-1111", "1111");
        biblioteca.doCheckOut("Book A");
        biblioteca.doCheckOut("Movie A");
        assertEquals("Biblioteca should have 4 item after checkout with user", 4, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
    }

    @Test
    public void bibliotecaShouldHaveReturn() {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.login("111-1111", "1111");
        biblioteca.doCheckOut("Book A");
        biblioteca.doCheckOut("Movie A");
        assertEquals("Biblioteca should have 4 items ", 4, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
        biblioteca.logout();
        biblioteca.doReturn("Book A");
        assertEquals("Biblioteca should have 4 item after return with no user", 4, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
        biblioteca.login("111-1111", "1111");
        biblioteca.doReturn("Book A");
        assertEquals("Biblioteca should have 5 items after return with user", 5, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
        biblioteca.login("222-2222", "2222");
        biblioteca.doReturn("Movie A");
        assertEquals("Biblioteca should have 5 items after return with wrong user", 5, biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).size());
    }

    @Test
    public void rentableShouldHaveCheckOutAndReturn() {
        Rentable item = new Book("Title","Author","Date");
        User user = new User("111-1111", "1234", "Name", "Email", "Phone");
        assertEquals("Book should available", true, item.isAvailable());
        item.doCheckOut(user);
        assertEquals("Book should not available after check out", false, item.isAvailable());
        assertEquals("Borrower should be user 111-1111", user.getId(), item.getBorrower().getId());
        item.doReturn();
        assertEquals("Book should available after return", true, item.isAvailable());
        assertEquals("Borrower should be none", null, item.getBorrower());
    }

    @Test
    public void bookShouldHaveInformation() {
        Book book = new Book("Title","Author","Date");
        assertEquals("Book should have a title", "Title", book.getTitle());
        assertEquals("Book should have an author", "Author", book.getAuthor());
        assertEquals("Book should have a publish date", "Date", book.getPublishDate());
        assertEquals("Book should have a borrower", null, book.getBorrower());
    }

    @Test
    public void movieShouldHaveInformation() {
        Movie movie = new Movie("Title",2020,"Director",10);
        assertEquals("Movie should have a title", "Title", movie.getTitle());
        assertEquals("Movie should have a year", 2020, movie.getYear());
        assertEquals("Movie should have a director", "Director", movie.getDirector());
        assertEquals("Movie should have a rating", 10, movie.getRating());
        assertEquals("Movie should have a borrower", null, movie.getBorrower());
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
        user.checkOutItem(book);
        user.checkOutItem(movie);
        assertEquals("User should have 2 items", 2, user.getItems().size());
        user.returnItem(book);
        user.returnItem(movie);
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

    @Test
    public void bibliotecaShouldHaveCheckMyItems() {
        Biblioteca biblioteca = new Biblioteca();
        assertEquals("There is no items if not login", 0, biblioteca.getMyItems().size());
        biblioteca.login("111-1111", "1111");
        biblioteca.doCheckOut("Book A");
        biblioteca.doCheckOut("Book B");
        biblioteca.doCheckOut("Movie A");
        assertEquals("User 111-1111 should have 3 items", 3, biblioteca.getMyItems().size());
        biblioteca.doReturn("Movie A");
        biblioteca.doReturn("Book A");
        assertEquals("User 111-1111 should have 1 items", 1, biblioteca.getMyItems().size());
    }
}
