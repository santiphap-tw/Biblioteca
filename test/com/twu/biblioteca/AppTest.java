package com.twu.biblioteca;


import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class AppTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;


    private void trackPrint() {
        System.setOut(new PrintStream(outContent));
    }

    private void trackPrint(String input) {
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        System.setOut(new PrintStream(outContent));
        System.setIn(inContent);
    }

    @After
    public void untrackPrint() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    private String getTrackedPrint(){
        return outContent.toString();
    }

    @Test
    public void bibliotecaShouldHaveWelcomeMessage() {
        trackPrint("0");
        BibliotecaApp biblioteca = new BibliotecaApp();
        biblioteca.start();
        String[] outputLine = getTrackedPrint().split("\n");
        String firstLine = outputLine[0];
        assertEquals("Biblioteca should have a welcome message",true, firstLine.toLowerCase().contains("welcome"));
    }

    @Test
    public void showListOfBooksShouldShowBooks() {
        trackPrint();
        BibliotecaApp biblioteca = new BibliotecaApp();
        biblioteca.showListOfBooks();
        String[] outputLine = getTrackedPrint().split("\n");
        String firstLine = outputLine[0];
        assertEquals("showListOfBooks should show a header",true, firstLine.toLowerCase().contains("list of books"));
        int numberOfBooks = outputLine.length-1;
        assertEquals("showListOfBooks should show at least 1 book", true, numberOfBooks > 0);
    }

    @Test
    public void bibliotecaShouldShowOptionsAfterWelcome() {
        trackPrint("0");
        BibliotecaApp biblioteca = new BibliotecaApp();
        biblioteca.start();
        String[] outputLine = getTrackedPrint().split("\n");
        String secondLine = outputLine.length > 1 ? outputLine[1] : "";
        assertEquals("Biblioteca should show options after welcome message appeared",true, secondLine.toLowerCase().contains("what would you like to do?"));
    }

    @Test
    public void bibliotecaShouldHaveShowBooksOptionAtOption1() {
        trackPrint("1 0");
        BibliotecaApp biblioteca = new BibliotecaApp();
        biblioteca.showOptions();
        String[] outputLine = getTrackedPrint().split(">>>")[1].split("\n");
        String firstLine = outputLine[0];
        assertEquals("Biblioteca option 1 should show a header",true, firstLine.toLowerCase().contains("list of books"));
        int numberOfBooks = outputLine.length-1;
        assertEquals("Biblioteca option 1 should show at least 1 book", true, numberOfBooks > 0);
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
    }

    @Test
    public void bibliotecaShouldNotifyInvalidOption() {
        trackPrint("Hello 0");
        BibliotecaApp biblioteca = new BibliotecaApp();
        biblioteca.showOptions();
        String[] outputLine = getTrackedPrint().split(">>>")[1].split("\n");
        String firstLine = outputLine[0];
        assertEquals("Biblioteca should notify for invalid options",true, firstLine.toLowerCase().contains("valid"));
    }

    @Test
    public void bibliotecaShouldQuit() {
        trackPrint("0");
        BibliotecaApp biblioteca = new BibliotecaApp();
        biblioteca.showOptions();
        String[] outputLine = getTrackedPrint().split(">>>")[1].split("\n");
        String firstLine = outputLine[0];
        assertEquals("Biblioteca should quit",true, firstLine.toLowerCase().contains("thank you"));
    }

    @Test
    public void bookShouldHaveCheckOut() {
        Book book = new Book("Title","Author","Date");
        assertEquals("Book should available", true, book.isAvailable());
        book.checkOut();
        assertEquals("Book should not available after check out", false, book.isAvailable());
        book.checkIn();
        assertEquals("Book should available after return", true, book.isAvailable());
    }

    @Test
    public void bibliotecaShouldHaveCheckOut() {
        trackPrint("0");
        BibliotecaApp biblioteca = new BibliotecaApp();
        biblioteca.start();
        assertEquals("Biblioteca should have 3 books at start", 3, biblioteca.getBooks().size());
        biblioteca.checkOut(1);
        assertEquals("Biblioteca should have 2 books after checkout", 2, biblioteca.getBooks().size());
    }

    @Test
    public void bibliotecaShouldHaveCheckOutOptionAtOption2() {
        trackPrint("2 1 0");
        BibliotecaApp biblioteca = new BibliotecaApp();
        biblioteca.showOptions();
        assertEquals("Biblioteca should have 2 books after checkout", 2, biblioteca.getBooks().size());
    }
}
