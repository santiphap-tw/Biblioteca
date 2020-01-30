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
        // Given
        biblioteca = new Biblioteca();
        biblioteca.user().login("111-1111", "1111");
        biblioteca.doCheckOut("Book A");
        biblioteca.doCheckOut("Movie A");
        biblioteca.doCheckOut("Book B");
    }

    @Test
    public void showTest() {
        // Given
        ShowOperation showOperation = new ShowOperation("", biblioteca);
        ArrayList<String> output;
        ArrayList<String> expectedOutput;
        ArrayList<Rental> collection;

        // Default Test
        // When
        output = showOperation.run("");
        // Then
        assertEquals("default show should be available ", showOperation.run("available"), output);

        // Available test
        // Given
        collection = biblioteca.getItems(Biblioteca.FILTER.AVAILABLE);
        expectedOutput = ItemPrinter.collection(collection, Rental.class, false);
        // When
        output = showOperation.run("available");
        // Then
        assertEquals("show available should be as expected", expectedOutput, output);

        // Not Available test
        // Given
        collection = biblioteca.getItems(Biblioteca.FILTER.NOT_AVAILABLE);
        expectedOutput = ItemPrinter.collection(collection, Rental.class, true);
        // When
        output = showOperation.run("not available");
        // Then
        assertEquals("show not available should be as expected", expectedOutput, output);

        // Available test
        // Given
        collection = biblioteca.getItems(Biblioteca.FILTER.ALL);
        expectedOutput = ItemPrinter.collection(collection, Rental.class, true);
        // When
        output = showOperation.run("all");
        // Then
        assertEquals("show all should be as expected", expectedOutput, output);
    }

    @Test
    public void showBookTest() {
        // Given
        ShowOperation showOperation = new ShowOperation("", biblioteca, Book.class);
        ArrayList<String> output;
        ArrayList<String> expectedOutput;
        ArrayList<Rental> collection;

        // Default Test
        // When
        output = showOperation.run("");
        // Then
        assertEquals("default show book should be available ", showOperation.run("available"), output);

        // Available test
        // Given
        collection = biblioteca.getItems(Biblioteca.FILTER.AVAILABLE);
        expectedOutput = ItemPrinter.collection(collection, Book.class, false);
        // When
        output = showOperation.run("available");
        // Then
        assertEquals("show book available should be as expected", expectedOutput, output);

        // Not Available test
        // When
        // Given
        collection = biblioteca.getItems(Biblioteca.FILTER.NOT_AVAILABLE);
        expectedOutput = ItemPrinter.collection(collection, Book.class, true);
        output = showOperation.run("not available");
        // Then
        assertEquals("show book not available should be as expected", expectedOutput, output);

        // Available test
        // Given
        collection = biblioteca.getItems(Biblioteca.FILTER.ALL);
        expectedOutput = ItemPrinter.collection(collection, Book.class, true);
        // When
        output = showOperation.run("all");
        // Then
        assertEquals("show book all should be as expected", expectedOutput, output);
    }

    @Test
    public void showMovieTest() {
        // Setup operation
        // Then
        ShowOperation showOperation = new ShowOperation("", biblioteca, Movie.class);
        ArrayList<String> output;
        ArrayList<String> expectedOutput;
        ArrayList<Rental> collection;

        // Default Test
        // When
        output = showOperation.run("");
        // Then
        assertEquals("default show movie should be available ", showOperation.run("available"), output);

        // Available test
        // Given
        collection = biblioteca.getItems(Biblioteca.FILTER.AVAILABLE);
        expectedOutput = ItemPrinter.collection(collection, Movie.class, false);
        // When
        output = showOperation.run("available");
        // Then
        assertEquals("show movie available should be as expected", expectedOutput, output);

        // Not Available test
        // Given
        collection = biblioteca.getItems(Biblioteca.FILTER.NOT_AVAILABLE);
        expectedOutput = ItemPrinter.collection(collection, Movie.class, true);
        // When
        output = showOperation.run("not available");
        // Then
        assertEquals("show movie not available should be as expected", expectedOutput, output);

        // Available test
        // Given
        collection = biblioteca.getItems(Biblioteca.FILTER.ALL);
        expectedOutput = ItemPrinter.collection(collection, Movie.class, true);
        // When
        output = showOperation.run("all");
        // Then
        assertEquals("show movie all should be as expected", expectedOutput, output);
    }
}
