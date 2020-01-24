package com.twu.biblioteca;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class AppTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void bibliotecaShouldHaveWelcomeMessage() {
        new BibliotecaApp();
        String[] outputLine = outContent.toString().split("\n");
        String firstLine = outputLine[0];
        assertEquals("Biblioteca should have a welcome message",true, firstLine.toLowerCase().contains("welcome"));
    }

    @Test
    public void bibliotecaShouldShowBooksAfterWelcome() {
        new BibliotecaApp();
        String[] outputLine = outContent.toString().split("\n");
        String secondLine = outputLine.length > 1 ? outputLine[1] : "";
        assertEquals("Biblioteca should show a list of books after welcome message appeared",true, secondLine.toLowerCase().contains("list of books"));
        int numberOfBooks = outputLine.length-2;
        assertEquals("Biblioteca should show at least 1 book after welcome message appeared", true, numberOfBooks > 0);
    }

    @Test
    public void bookShouldHaveTitleAuthorDate() {
        Book book = new Book("Title","Author","Date");
        assertEquals("Book should have a title", "Title", book.getTitle());
        assertEquals("Book should have an author", "Author", book.getAuthor());
        assertEquals("Book should have a publish date", "Date", book.getPublishDate());
    }
}
