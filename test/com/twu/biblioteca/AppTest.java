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
        System.setIn(inContent);
        trackPrint();
    }

    private void trackPrint(String[] inputs) {
        String input = String.join(System.getProperty("line.separator"), inputs);
        trackPrint(input);
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
        String[] output_allLine = getTrackedPrint().split("\n");
        String firstLine = output_allLine[0];
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
        String[] output_allLine = getTrackedPrint().split("\n");
        String secondLine = output_allLine.length > 1 ? output_allLine[1] : "";
        assertEquals("Biblioteca should show options after welcome message appeared",true, secondLine.toLowerCase().contains("what would you like to do?"));
    }

    @Test
    public void bibliotecaShouldHaveShowBooksOptionAtOption1() {
        trackPrint(new String[] {"1", "0"});
        BibliotecaApp biblioteca = new BibliotecaApp();
        biblioteca.showOptions();
        String[] output_allLine = getTrackedPrint().split(">>>")[1].split("\n");
        String firstLine = output_allLine[0];
        assertEquals("Biblioteca option 1 should show a header",true, firstLine.toLowerCase().contains("list of books"));
        int numberOfBooks = output_allLine.length-1;
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
        String[] output_allLine = getTrackedPrint().split("\n");
        String firstLine = output_allLine[0];
        assertEquals("showListOfBooksDetailed should have title header",true, firstLine.toLowerCase().contains("title"));
        assertEquals("showListOfBooksDetailed should have author header",true, firstLine.toLowerCase().contains("author"));
        assertEquals("showListOfBooksDetailed should have publish date header",true, firstLine.toLowerCase().contains("publish date"));
        int numberOfBooks = output_allLine.length-1;
        assertEquals("showListOfBooksDetailed should show at least 1 book", true, numberOfBooks > 0);
        String firstBook = output_allLine[1];
        assertEquals("showListOfBooksDetailed should show books with details", true, firstBook.contains("|"));
    }

    @Test
    public void bibliotecaShouldNotifyInvalidOption() {
        trackPrint(new String[] {"Hello", "0"});
        BibliotecaApp biblioteca = new BibliotecaApp();
        biblioteca.showOptions();
        String[] output_allLine = getTrackedPrint().split(">>>")[1].split("\n");
        String output = output_allLine[output_allLine.length-1];
        assertEquals("Biblioteca should notify for invalid options",true, output.toLowerCase().contains("valid"));
    }

    @Test
    public void bibliotecaShouldQuit() {
        trackPrint("0");
        BibliotecaApp biblioteca = new BibliotecaApp();
        biblioteca.showOptions();
        String[] output_allLine = getTrackedPrint().split(">>>")[1].split("\n");
        String output = output_allLine[output_allLine.length-1];
        assertEquals("Biblioteca should quit",true, output.toLowerCase().contains("thank you"));
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
        biblioteca.checkOut("Book A");
        assertEquals("Biblioteca should have 2 books after checkout", 2, biblioteca.getBooks().size());
        biblioteca.checkOut("Book B");
        assertEquals("Biblioteca should have 1 books after checkout", 1, biblioteca.getBooks().size());
    }

    @Test
    public void bibliotecaShouldHaveCheckOutOptionAtOption2() {
        trackPrint(new String[] {"2","Book A","2","Book A","0"});
        BibliotecaApp biblioteca = new BibliotecaApp();
        biblioteca.showOptions();
        assertEquals("Biblioteca should have 2 books after checkout", 2, biblioteca.getBooks().size());
        String[] output1_allLine = getTrackedPrint().split(">>>")[1].split("\n");
        String output1 = output1_allLine[output1_allLine.length-1];
        assertEquals("Checkout should notify when success", true, output1.toLowerCase().contains("thank you"));
        String[] output2_allLine = getTrackedPrint().split(">>>")[2].split("\n");
        String output2 = output2_allLine[output2_allLine.length-1];
        assertEquals("Checkout should notify when fail", true, output2.toLowerCase().contains("sorry"));
    }
}
