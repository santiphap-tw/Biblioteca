package com.twu.biblioteca;


import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

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

    @Test
    public void bookShouldHaveTitleAuthorPublishDate() {
        Book book = new Book("Title","Author","Date");
        assertEquals("Book should have a title", "Title", book.getTitle());
        assertEquals("Book should have an author", "Author", book.getAuthor());
        assertEquals("Book should have a publish date", "Date", book.getPublishDate());
    }

    @Test
    public void bookShouldHaveCheckOut() {
        Book book = new Book("Title","Author","Date");
        assertEquals("Book should available", true, book.isAvailable());
        book.doCheckOut();
        assertEquals("Book should not available after check out", false, book.isAvailable());
        book.doReturn();
        assertEquals("Book should available after return", true, book.isAvailable());
    }

    @Test
    public void bibliotecaShouldHaveCheckOut() {
        simulateInput("0");
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.start();
        assertEquals("Biblioteca should have 3 books at start", 3, biblioteca.getBooks(Biblioteca.BOOK_FILTER.AVAILABLE).size());
        biblioteca.doCheckOut("Book A");
        assertEquals("Biblioteca should have 2 books after checkout", 2, biblioteca.getBooks(Biblioteca.BOOK_FILTER.AVAILABLE).size());
        biblioteca.doCheckOut("Book B");
        assertEquals("Biblioteca should have 1 books after checkout", 1, biblioteca.getBooks(Biblioteca.BOOK_FILTER.AVAILABLE).size());
    }

    @Test
    public void bibliotecaShouldHaveReturn() {
        simulateInput("0");
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.start();
        biblioteca.doCheckOut("Book A");
        biblioteca.doCheckOut("Book B");
        assertEquals("Biblioteca should have 1 books at start", 1, biblioteca.getBooks(Biblioteca.BOOK_FILTER.AVAILABLE).size());
        biblioteca.doReturn("Book A");
        assertEquals("Biblioteca should have 2 books after return", 2, biblioteca.getBooks(Biblioteca.BOOK_FILTER.AVAILABLE).size());
    }

}
