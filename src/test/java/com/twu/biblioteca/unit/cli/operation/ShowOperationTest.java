package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.Formatter;
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
    public void shouldShowAvailableIfNoParameter() {
        // Given
        ShowOperation showOperation = new ShowOperation("", biblioteca);
        // When
        ArrayList<String> output = showOperation.run("");
        // Then
        assertEquals("default show should be available ", showOperation.run("available"), output);
    }

    @Test
    public void shouldShowAvailable() {
        // Given
        ShowOperation showOperation = new ShowOperation("", biblioteca);
        ArrayList<Rental> collection = biblioteca.getItems(Biblioteca.FILTER.AVAILABLE);
        // When
        ArrayList<String> output = showOperation.run("available");
        // Then
        ArrayList<String> expectedOutput = Formatter.collection(collection, Rental.class, false);
        assertEquals("show available should be as expected", expectedOutput, output);
    }

    @Test
    public void shouldShowNotAvailable() {
        // Given
        ShowOperation showOperation = new ShowOperation("", biblioteca);
        ArrayList<Rental> collection = biblioteca.getItems(Biblioteca.FILTER.NOT_AVAILABLE);
        // When
        ArrayList<String> output = showOperation.run("not available");
        // Then
        ArrayList<String> expectedOutput = Formatter.collection(collection, Rental.class, true);
        assertEquals("show not available should be as expected", expectedOutput, output);
    }

    @Test
    public void shouldShowAll() {
        // Given
        ShowOperation showOperation = new ShowOperation("", biblioteca);
        ArrayList<Rental> collection = biblioteca.getItems(Biblioteca.FILTER.ALL);
        // When
        ArrayList<String> output = showOperation.run("all");
        // Then
        ArrayList<String> expectedOutput = Formatter.collection(collection, Rental.class, true);
        assertEquals("show all should be as expected", expectedOutput, output);
    }

    @Test
    public void shouldShowAvailableBookIfNoParameter() {
        // Given
        ShowOperation showOperation = new ShowOperation("", biblioteca, Book.class);
        // When
        ArrayList<String> output = showOperation.run("");
        // Then
        assertEquals("default show book should be available ", showOperation.run("available"), output);
    }

    @Test
    public void shouldShowAvailableBook() {
        // Given
        ShowOperation showOperation = new ShowOperation("", biblioteca, Book.class);
        ArrayList<Rental> collection = biblioteca.getItems(Biblioteca.FILTER.AVAILABLE);
        // When
        ArrayList<String> output = showOperation.run("available");
        // Then
        ArrayList<String> expectedOutput = Formatter.collection(collection, Book.class, false);
        assertEquals("show book available should be as expected", expectedOutput, output);
    }

    @Test
    public void shouldShowNotAvailableBook() {
        // Given
        ShowOperation showOperation = new ShowOperation("", biblioteca, Book.class);
        ArrayList<Rental> collection = biblioteca.getItems(Biblioteca.FILTER.NOT_AVAILABLE);
        // When
        ArrayList<String> output = showOperation.run("not available");
        // Then
        ArrayList<String> expectedOutput = Formatter.collection(collection, Book.class, true);
        assertEquals("show book not available should be as expected", expectedOutput, output);
    }

    @Test
    public void shouldShowAllBook() {
        // Given
        ShowOperation showOperation = new ShowOperation("", biblioteca, Book.class);
        ArrayList<Rental> collection = biblioteca.getItems(Biblioteca.FILTER.ALL);
        // When
        ArrayList<String> output = showOperation.run("all");
        // Then
        ArrayList<String> expectedOutput = Formatter.collection(collection, Book.class, true);
        assertEquals("show book all should be as expected", expectedOutput, output);
    }

    @Test
    public void shouldShowAvailableMovieIfNoParameter() {
        // Given
        ShowOperation showOperation = new ShowOperation("", biblioteca, Movie.class);
        // When
        ArrayList<String> output = showOperation.run("");
        // Then
        assertEquals("default show movie should be available ", showOperation.run("available"), output);
    }

    @Test
    public void shouldShowAvailableMovie() {
        // Given
        ShowOperation showOperation = new ShowOperation("", biblioteca, Movie.class);
        ArrayList<Rental> collection = biblioteca.getItems(Biblioteca.FILTER.AVAILABLE);
        // When
        ArrayList<String> output = showOperation.run("available");
        // Then
        ArrayList<String> expectedOutput = Formatter.collection(collection, Movie.class, false);
        assertEquals("show movie available should be as expected", expectedOutput, output);
    }

    @Test
    public void shouldShowNotAvailableMovie() {
        // Given
        ShowOperation showOperation = new ShowOperation("", biblioteca, Movie.class);
        ArrayList<Rental> collection = biblioteca.getItems(Biblioteca.FILTER.NOT_AVAILABLE);
        // When
        ArrayList<String> output = showOperation.run("not available");
        // Then
        ArrayList<String> expectedOutput = Formatter.collection(collection, Movie.class, true);
        assertEquals("show movie not available should be as expected", expectedOutput, output);
    }

    @Test
    public void shouldShowAllMovie() {
        // Given
        ShowOperation showOperation = new ShowOperation("", biblioteca, Movie.class);
        ArrayList<Rental> collection = biblioteca.getItems(Biblioteca.FILTER.ALL);
        // When
        ArrayList<String> output = showOperation.run("all");
        // Then
        ArrayList<String> expectedOutput = Formatter.collection(collection, Movie.class, true);
        assertEquals("show movie all should be as expected", expectedOutput, output);
    }
}
