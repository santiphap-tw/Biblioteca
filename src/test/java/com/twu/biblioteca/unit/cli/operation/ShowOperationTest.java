package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.Formatter;
import com.twu.biblioteca.cli.operation.ShowOperation;
import com.twu.biblioteca.database.RentalDatabase;
import com.twu.biblioteca.database.UserDatabase;
import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Movie;
import com.twu.biblioteca.model.Rental;
import com.twu.biblioteca.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ShowOperationTest {

    private User sampleUser1 = UserDatabase.getInstance().getUsers().get(0);
    private Rental sampleItem1 = RentalDatabase.getInstance().getItems(item -> item.getClass() == Book.class).get(0);
    private Rental sampleItem2 = RentalDatabase.getInstance().getItems(item -> item.getClass() == Movie.class).get(0);
    private Rental sampleItem3 = RentalDatabase.getInstance().getItems(item -> item.getClass() == Book.class).get(1);

    @Before
    public void initialize(){
        // Given
        Biblioteca.getInstance().initialize();
        Biblioteca.getInstance().user().login(sampleUser1.getId(), sampleUser1.getPassword());
        Biblioteca.getInstance().doCheckOut(sampleItem1.getTitle());
        Biblioteca.getInstance().doCheckOut(sampleItem2.getTitle());
        Biblioteca.getInstance().doCheckOut(sampleItem3.getTitle());
    }

    @Test
    public void shouldShowAvailableIfNoParameter() {
        // Given
        ShowOperation showOperation = new ShowOperation("");
        // When
        ArrayList<String> output = showOperation.run("");
        // Then
        assertEquals("default show should be available ", showOperation.run("available"), output);
    }

    @Test
    public void shouldShowAvailable() {
        // Given
        ShowOperation showOperation = new ShowOperation("");
        ArrayList<Rental> collection = RentalDatabase.getInstance().getItems(RentalDatabase.Filter.AVAILABLE);
        // When
        ArrayList<String> output = showOperation.run("available");
        // Then
        ArrayList<String> expectedOutput = Formatter.items(collection, Rental.class, false);
        assertEquals("show available should be as expected", expectedOutput, output);
    }

    @Test
    public void shouldShowNotAvailable() {
        // Given
        ShowOperation showOperation = new ShowOperation("");
        ArrayList<Rental> collection = RentalDatabase.getInstance().getItems(RentalDatabase.Filter.NOT_AVAILABLE);
        // When
        ArrayList<String> output = showOperation.run("not available");
        // Then
        ArrayList<String> expectedOutput = Formatter.items(collection, Rental.class, true);
        assertEquals("show not available should be as expected", expectedOutput, output);
    }

    @Test
    public void shouldShowAll() {
        // Given
        ShowOperation showOperation = new ShowOperation("");
        ArrayList<Rental> collection = RentalDatabase.getInstance().getItems(RentalDatabase.Filter.ALL);
        // When
        ArrayList<String> output = showOperation.run("all");
        // Then
        ArrayList<String> expectedOutput = Formatter.items(collection, Rental.class, true);
        assertEquals("show all should be as expected", expectedOutput, output);
    }

    @Test
    public void shouldShowAvailableBookIfNoParameter() {
        // Given
        ShowOperation showOperation = new ShowOperation("", Book.class);
        // When
        ArrayList<String> output = showOperation.run("");
        // Then
        assertEquals("default show book should be available ", showOperation.run("available"), output);
    }

    @Test
    public void shouldShowAvailableBook() {
        // Given
        ShowOperation showOperation = new ShowOperation("", Book.class);
        ArrayList<Rental> collection = RentalDatabase.getInstance().getItems(RentalDatabase.Filter.AVAILABLE);
        // When
        ArrayList<String> output = showOperation.run("available");
        // Then
        ArrayList<String> expectedOutput = Formatter.items(collection, Book.class, false);
        assertEquals("show book available should be as expected", expectedOutput, output);
    }

    @Test
    public void shouldShowNotAvailableBook() {
        // Given
        ShowOperation showOperation = new ShowOperation("", Book.class);
        ArrayList<Rental> collection = RentalDatabase.getInstance().getItems(RentalDatabase.Filter.NOT_AVAILABLE);
        // When
        ArrayList<String> output = showOperation.run("not available");
        // Then
        ArrayList<String> expectedOutput = Formatter.items(collection, Book.class, true);
        assertEquals("show book not available should be as expected", expectedOutput, output);
    }

    @Test
    public void shouldShowAllBook() {
        // Given
        ShowOperation showOperation = new ShowOperation("", Book.class);
        ArrayList<Rental> collection = RentalDatabase.getInstance().getItems(RentalDatabase.Filter.ALL);
        // When
        ArrayList<String> output = showOperation.run("all");
        // Then
        ArrayList<String> expectedOutput = Formatter.items(collection, Book.class, true);
        assertEquals("show book all should be as expected", expectedOutput, output);
    }

    @Test
    public void shouldShowAvailableMovieIfNoParameter() {
        // Given
        ShowOperation showOperation = new ShowOperation("", Movie.class);
        // When
        ArrayList<String> output = showOperation.run("");
        // Then
        assertEquals("default show movie should be available ", showOperation.run("available"), output);
    }

    @Test
    public void shouldShowAvailableMovie() {
        // Given
        ShowOperation showOperation = new ShowOperation("", Movie.class);
        ArrayList<Rental> collection = RentalDatabase.getInstance().getItems(RentalDatabase.Filter.AVAILABLE);
        // When
        ArrayList<String> output = showOperation.run("available");
        // Then
        ArrayList<String> expectedOutput = Formatter.items(collection, Movie.class, false);
        assertEquals("show movie available should be as expected", expectedOutput, output);
    }

    @Test
    public void shouldShowNotAvailableMovie() {
        // Given
        ShowOperation showOperation = new ShowOperation("", Movie.class);
        ArrayList<Rental> collection = RentalDatabase.getInstance().getItems(RentalDatabase.Filter.NOT_AVAILABLE);
        // When
        ArrayList<String> output = showOperation.run("not available");
        // Then
        ArrayList<String> expectedOutput = Formatter.items(collection, Movie.class, true);
        assertEquals("show movie not available should be as expected", expectedOutput, output);
    }

    @Test
    public void shouldShowAllMovie() {
        // Given
        ShowOperation showOperation = new ShowOperation("", Movie.class);
        ArrayList<Rental> collection = RentalDatabase.getInstance().getItems(RentalDatabase.Filter.ALL);
        // When
        ArrayList<String> output = showOperation.run("all");
        // Then
        ArrayList<String> expectedOutput = Formatter.items(collection, Movie.class, true);
        assertEquals("show movie all should be as expected", expectedOutput, output);
    }
}
