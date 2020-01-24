package com.twu.biblioteca;


import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

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
        BibliotecaApp biblioteca = new BibliotecaApp();
        biblioteca.start();
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
    public void bibliotecaShouldShowOptionsAfterWelcome() {
        trackPrint();
        BibliotecaApp biblioteca = new BibliotecaApp();
        biblioteca.start();
        String[] outputLine = getTrackedPrint().split("\n");
        String secondLine = outputLine.length > 1 ? outputLine[1] : "";
        assertEquals("Biblioteca should show options after welcome message appeared",true, secondLine.toLowerCase().contains("what would you like to do?"));
        untrackPrint();
    }

    @Test
    public void bibliotecaShouldHaveShowBooksOption() {
        BibliotecaApp biblioteca = new BibliotecaApp();
        trackPrint();
        biblioteca.showOptions();
        String[] outputLine = getTrackedPrint().split("\n");
        int numberOfOptions = outputLine.length-1;
        assertEquals("showOptions should have at least 1 option", true, numberOfOptions > 0);
        for(int option = 1; option <= numberOfOptions; option++){
            assertEquals("showOptions should have show books option",true, outputLine[option].toLowerCase().contains("show list of books"));
        }
        untrackPrint();
    }

    @Test
    public void bibloptecaOption1ShouldShowBooks() {
        BibliotecaApp biblioteca = new BibliotecaApp();
        trackPrint();
        biblioteca.selectOption("1");
        String[] outputLine = getTrackedPrint().split("\n");
        String firstLine = outputLine[0];
        assertEquals("showListOfBooks should show a header",true, firstLine.toLowerCase().contains("list of books"));
        int numberOfBooks = outputLine.length-1;
        assertEquals("showListOfBooks should show at least 1 book", true, numberOfBooks > 0);
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
