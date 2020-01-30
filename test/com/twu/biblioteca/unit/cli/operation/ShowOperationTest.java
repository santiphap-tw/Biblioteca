package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.ItemPrinter;
import com.twu.biblioteca.cli.operation.ShowOperation;
import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Movie;
import com.twu.biblioteca.model.Rental;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ShowOperationTest {

    private Biblioteca biblioteca;

    @Before
    public void initialize(){
        biblioteca = new Biblioteca();
        biblioteca.user().login("111-1111", "1111");
        biblioteca.doCheckOut("Book A");
        biblioteca.doCheckOut("Movie A");
        biblioteca.doCheckOut("Book B");
    }

    @Test
    public void showTest() {
        // Setup operation
        ShowOperation showOperation = new ShowOperation("", biblioteca);
        ArrayList<String> output;
        ArrayList<String> expectedOutput;
        ArrayList<Rental> collection;

        // Default Test
        output = showOperation.run("");
        assertEquals("default show should be available ", showOperation.run("available"), output);

        // Available test
        output = showOperation.run("available");
        collection = biblioteca.getItems(Biblioteca.FILTER.AVAILABLE);
        expectedOutput = ItemPrinter.collection(collection, Rental.class, false);
        assertEquals("show available should be as expected", expectedOutput, output);

        // Not Available test
        output = showOperation.run("not available");
        collection = biblioteca.getItems(Biblioteca.FILTER.NOT_AVAILABLE);
        expectedOutput = ItemPrinter.collection(collection, Rental.class, true);
        assertEquals("show not available should be as expected", expectedOutput, output);

        // Available test
        output = showOperation.run("all");
        collection = biblioteca.getItems(Biblioteca.FILTER.ALL);
        expectedOutput = ItemPrinter.collection(collection, Rental.class, true);
        assertEquals("show all should be as expected", expectedOutput, output);
    }

    @Test
    public void showBookTest() {
        // Setup operation
        ShowOperation showOperation = new ShowOperation("", biblioteca, Book.class);
        ArrayList<String> output;
        ArrayList<String> expectedOutput;
        ArrayList<Rental> collection;

        // Default Test
        output = showOperation.run("");
        assertEquals("default show book should be available ", showOperation.run("available"), output);

        // Available test
        output = showOperation.run("available");
        collection = biblioteca.getItems(Biblioteca.FILTER.AVAILABLE);
        expectedOutput = ItemPrinter.collection(collection, Book.class, false);
        assertEquals("show book available should be as expected", expectedOutput, output);

        // Not Available test
        output = showOperation.run("not available");
        collection = biblioteca.getItems(Biblioteca.FILTER.NOT_AVAILABLE);
        expectedOutput = ItemPrinter.collection(collection, Book.class, true);
        assertEquals("show book not available should be as expected", expectedOutput, output);

        // Available test
        output = showOperation.run("all");
        collection = biblioteca.getItems(Biblioteca.FILTER.ALL);
        expectedOutput = ItemPrinter.collection(collection, Book.class, true);
        assertEquals("show book all should be as expected", expectedOutput, output);
    }

    @Test
    public void showMovieTest() {
        // Setup operation
        ShowOperation showOperation = new ShowOperation("", biblioteca, Movie.class);
        ArrayList<String> output;
        ArrayList<String> expectedOutput;
        ArrayList<Rental> collection;

        // Default Test
        output = showOperation.run("");
        assertEquals("default show movie should be available ", showOperation.run("available"), output);

        // Available test
        output = showOperation.run("available");
        collection = biblioteca.getItems(Biblioteca.FILTER.AVAILABLE);
        expectedOutput = ItemPrinter.collection(collection, Movie.class, false);
        assertEquals("show movie available should be as expected", expectedOutput, output);

        // Not Available test
        output = showOperation.run("not available");
        collection = biblioteca.getItems(Biblioteca.FILTER.NOT_AVAILABLE);
        expectedOutput = ItemPrinter.collection(collection, Movie.class, true);
        assertEquals("show movie not available should be as expected", expectedOutput, output);

        // Available test
        output = showOperation.run("all");
        collection = biblioteca.getItems(Biblioteca.FILTER.ALL);
        expectedOutput = ItemPrinter.collection(collection, Movie.class, true);
        assertEquals("show movie all should be as expected", expectedOutput, output);
    }
}
