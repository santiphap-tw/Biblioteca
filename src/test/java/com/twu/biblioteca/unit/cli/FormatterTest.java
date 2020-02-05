package com.twu.biblioteca.unit.cli;

import com.twu.biblioteca.cli.Formatter;
import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Movie;
import com.twu.biblioteca.model.Rental;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class FormatterTest {

    @Test
    public void shouldReturnRentalCollection() {
        // Given
        ArrayList<Rental> collection = new ArrayList<>();
        Book book = new Book("Book");
        Movie movie = new Movie("movie");
        collection.add(book);
        collection.add(movie);
        // When
        ArrayList<String> output = Formatter.items(collection, Rental.class, false);
        // Then
        boolean hasBookLabel = output.stream().anyMatch(text -> text.contains("Books List"));
        assertEquals("Rental collection should have book header", true, hasBookLabel);
        boolean hasMovieLabel = output.stream().anyMatch(text -> text.contains("Movies List"));
        assertEquals("Rental collection should have movie header", true, hasMovieLabel);
        int countLine = output.size();
        assertEquals("Rental collection should have 6 line", 6, countLine);
    }

    @Test
    public void shouldReturnBookCollection() {
        // Given
        ArrayList<Rental> collection = new ArrayList<>();
        Book book = new Book("Book");
        Movie movie = new Movie("movie");
        collection.add(book);
        collection.add(movie);
        // When
        ArrayList<String> output = Formatter.items(collection, Book.class, false);
        // Then
        boolean hasBookLabel = output.stream().anyMatch(text -> text.contains("Books List"));
        assertEquals("Book collection should have book header", true, hasBookLabel);
        int countLine = output.size();
        assertEquals("Book collection should have 3 line", 3, countLine);
    }

    @Test
    public void shouldReturnMovieCollection() {
        // Given
        ArrayList<Rental> collection = new ArrayList<>();
        Book book = new Book("Book");
        Movie movie = new Movie("movie");
        collection.add(book);
        collection.add(movie);
        // When
        ArrayList<String> output = Formatter.items(collection, Movie.class, false);
        // Then
        boolean hasMovieLabel = output.stream().anyMatch(text -> text.contains("Movies List"));
        assertEquals("Movie collection should have movie header", true, hasMovieLabel);
        int countLine = output.size();
        assertEquals("Movie collection should have 3 line", 3, countLine);
    }
}
