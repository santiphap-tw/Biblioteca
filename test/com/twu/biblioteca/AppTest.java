package com.twu.biblioteca;


import com.twu.biblioteca.model.Book;
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
        simulateInput("exit");
        BibliotecaApp app = new BibliotecaApp();
        app.start();
    }

    @Test(timeout=1000)
    public void bibliotecaAppHaveShowCommand() {
        simulateInput(new String[] {"show","exit"});
        BibliotecaApp app = new BibliotecaApp();
        app.start();
    }

    @Test(timeout=1000)
    public void bibliotecaAppHaveCheckOutCommand() {
        simulateInput(new String[] {"checkout Book A","exit"});
        Biblioteca biblioteca = new Biblioteca();
        BibliotecaApp app = new BibliotecaApp(biblioteca);
        app.start();
        assertEquals("Biblioteca app should have 2 books after checkout", 2, biblioteca.getBooks(Biblioteca.BOOK_FILTER.AVAILABLE).size());
    }

    @Test(timeout=1000)
    public void bibliotecaAppHaveReturnCommand() {
        simulateInput(new String[] {"checkout Book A","checkout Book B","return Book A","exit"});
        Biblioteca biblioteca = new Biblioteca();
        BibliotecaApp app = new BibliotecaApp(biblioteca);
        app.start();
        assertEquals("Biblioteca app should have 2 books after return", 2, biblioteca.getBooks(Biblioteca.BOOK_FILTER.AVAILABLE).size());
    }

    @Test
    public void bibliotecaShouldHaveCheckOut() {
        Biblioteca biblioteca = new Biblioteca();
        assertEquals("Biblioteca should have 3 books at start", 3, biblioteca.getBooks(Biblioteca.BOOK_FILTER.AVAILABLE).size());
        biblioteca.doCheckOut("Book A");
        assertEquals("Biblioteca should have 2 books after checkout", 2, biblioteca.getBooks(Biblioteca.BOOK_FILTER.AVAILABLE).size());
        biblioteca.doCheckOut("Book B");
        assertEquals("Biblioteca should have 1 books after checkout", 1, biblioteca.getBooks(Biblioteca.BOOK_FILTER.AVAILABLE).size());
    }

    @Test
    public void bibliotecaShouldHaveReturn() {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.doCheckOut("Book A");
        biblioteca.doCheckOut("Book B");
        assertEquals("Biblioteca should have 1 books at start", 1, biblioteca.getBooks(Biblioteca.BOOK_FILTER.AVAILABLE).size());
        biblioteca.doReturn("Book A");
        assertEquals("Biblioteca should have 2 books after return", 2, biblioteca.getBooks(Biblioteca.BOOK_FILTER.AVAILABLE).size());
    }

    @Test
    public void bookShouldHaveTitleAuthorAndPublishDate() {
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
}
