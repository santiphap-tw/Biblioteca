package com.twu.biblioteca.unit.cli;

import com.twu.biblioteca.cli.ItemPrinter;
import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Movie;
import com.twu.biblioteca.model.Rental;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ItemPrinterTest {

    @Test
    public void shouldReturnRentalHeader() {
        // Given
        // When
        ArrayList<String> output = ItemPrinter.header(Rental.class, false);
        // Then
        boolean hasLabel = output.stream().anyMatch(text -> text.contains("Items List"));
        assertEquals("Rental header should have a label", true, hasLabel);
        long countField = output.get(1).chars().filter(ch -> ch == '|').count() + 1;
        assertEquals("Rental header should have 2 field", 2, countField);
    }

    @Test
    public void shouldReturnRentalItem() {
        // Given
        Rental item = new Rental() {
        };
        // When
        ArrayList<String> output = ItemPrinter.item(item, false);
        // Then
        long countField = output.get(0).chars().filter(ch -> ch == '|').count() + 1;
        assertEquals("Rental item should have 2 field", 2, countField);
    }

    @Test
    public void shouldReturnRentalCollection() {
        // Given
        ArrayList<Rental> collection = new ArrayList<>();
        Book book = new Book("Book");
        Movie movie = new Movie("movie");
        collection.add(book);
        collection.add(movie);
        // When
        ArrayList<String> output = ItemPrinter.collection(collection, Rental.class, false);
        // Then
        boolean hasBookLabel = output.stream().anyMatch(text -> text.contains("Books List"));
        assertEquals("Rental collection should have book header", true, hasBookLabel);
        boolean hasMovieLabel = output.stream().anyMatch(text -> text.contains("Movies List"));
        assertEquals("Rental collection should have movie header", true, hasMovieLabel);
        int countLine = output.size();
        assertEquals("Rental collection should have 6 line", 6, countLine);
    }

    @Test
    public void shouldReturnBookHeader() {
        // Given
        // When
        ArrayList<String> output = ItemPrinter.header(Book.class, false);
        // Then
        boolean hasLabel = output.stream().anyMatch(text -> text.contains("Books List"));
        assertEquals("Book header should have a label", true, hasLabel);
        long countField = output.get(1).chars().filter(ch -> ch == '|').count() + 1;
        assertEquals("Book header should have 3 field", 3, countField);
    }

    @Test
    public void shouldReturnBookItem() {
        // Given
        Book item = new Book("Book");
        // When
        ArrayList<String> output = ItemPrinter.item(item, false);
        // Then
        long countField = output.get(0).chars().filter(ch -> ch == '|').count() + 1;
        assertEquals("Book item should have 3 field", 3, countField);
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
        ArrayList<String> output = ItemPrinter.collection(collection, Book.class, false);
        // Then
        boolean hasBookLabel = output.stream().anyMatch(text -> text.contains("Books List"));
        assertEquals("Book collection should have book header", true, hasBookLabel);
        int countLine = output.size();
        assertEquals("Book collection should have 3 line", 3, countLine);
    }

    @Test
    public void shouldReturnMovieHeader() {
        // Given
        // When
        ArrayList<String> output = ItemPrinter.header(Movie.class, false);
        // Then
        boolean hasLabel = output.stream().anyMatch(text -> text.contains("Movies List"));
        assertEquals("Movie header should have a label", true, hasLabel);
        long countField = output.get(1).chars().filter(ch -> ch == '|').count() + 1;
        assertEquals("Movie header should have 4 field", 4, countField);
    }

    @Test
    public void shouldReturnMovieItem() {
        // Given
        Movie item = new Movie("movie");
        // When
        ArrayList<String> output = ItemPrinter.item(item, false);
        // Then
        long countField = output.get(0).chars().filter(ch -> ch == '|').count() + 1;
        assertEquals("Movie item should have 4 field", 4, countField);
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
        ArrayList<String> output = ItemPrinter.collection(collection, Movie.class, false);
        // Then
        boolean hasMovieLabel = output.stream().anyMatch(text -> text.contains("Movies List"));
        assertEquals("Movie collection should have movie header", true, hasMovieLabel);
        int countLine = output.size();
        assertEquals("Movie collection should have 3 line", 3, countLine);
    }
}
