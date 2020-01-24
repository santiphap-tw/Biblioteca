package com.twu.biblioteca;


import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class AppTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private void trackPrint() {
        System.setOut(new PrintStream(outContent));
    }

    private void untrackPrint() {
        System.setOut(originalOut);
    }

    private String getTrackedPrint(){
        return outContent.toString();
    }

    @Test
    public void bibliotecaShouldHaveWelcomeMessage() {
        trackPrint();
        new BibliotecaApp();
        String[] outputLine = getTrackedPrint().split("\n");
        String firstLine = outputLine[0];
        assertEquals("Biblioteca should have a welcome message",true, firstLine.toLowerCase().contains("welcome"));
        untrackPrint();
    }

    @Test
    public void bibliotecaShouldShowBooks() {
        BibliotecaApp biblioteca = new BibliotecaApp();
        trackPrint();
        biblioteca.showListOfBooks();
        String[] outputLine = getTrackedPrint().split("\n");
        String firstLine = outputLine[0];
        assertEquals("showListOfBooks should show a header",true, firstLine.toLowerCase().contains("list of books"));
        int numberOfBooks = outputLine.length-1;
        assertEquals("showListOfBooks should show at least 1 book", true, numberOfBooks > 0);
        untrackPrint();
    }

    @Test
    public void bibliotecaShouldShowBooksAfterWelcome() {
        trackPrint();
        new BibliotecaApp();
        String[] outputLine = getTrackedPrint().split("\n");
        String secondLine = outputLine.length > 1 ? outputLine[1] : "";
        assertEquals("Biblioteca should show a list of books after welcome message appeared",true, secondLine.toLowerCase().contains("list of books"));
        untrackPrint();
    }

    @Test
    public void bookShouldHaveTitleAuthorPublishDate() {
        Book book = new Book("Title","Author","Date");
        assertEquals("Book should have a title", "Title", book.getTitle());
        assertEquals("Book should have an author", "Author", book.getAuthor());
        assertEquals("Book should have a publish date", "Date", book.getPublishDate());
    }

    @Test
    public void bibliotecaShouldShowDetailedBooks() {
        BibliotecaApp biblioteca = new BibliotecaApp();
        trackPrint();
        biblioteca.showListOfBooksDetailed();
        String[] outputLine = getTrackedPrint().split("\n");
        String firstLine = outputLine[0];
        assertEquals("showListOfBooksDetailed should have title header",true, firstLine.toLowerCase().contains("title"));
        assertEquals("showListOfBooksDetailed should have author header",true, firstLine.toLowerCase().contains("author"));
        assertEquals("showListOfBooksDetailed should have publish date header",true, firstLine.toLowerCase().contains("publish date"));
        int numberOfBooks = outputLine.length-1;
        assertEquals("showListOfBooksDetailed should show at least 1 book", true, numberOfBooks > 0);
        String firstBook = outputLine[1];
        assertEquals("showListOfBooksDetailed should show books with details", true, firstLine.contains("|"));
        untrackPrint();
    }
}
