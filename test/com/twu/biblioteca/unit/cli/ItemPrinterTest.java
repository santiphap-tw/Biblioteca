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

    private ArrayList<Rental> collection;

    @Before
    public void initialize() {
        // Given
        collection = new ArrayList<>();
        Book book = new Book("Book");
        Movie movie = new Movie("movie");
        collection.add(book);
        collection.add(movie);
    }

    @Test
    public void rentalTest() {
        ArrayList<String> output;

        // Header
        // When
        output = ItemPrinter.header(Rental.class, false);
        // Then
        boolean hasLabel = output.stream().anyMatch(text -> text.contains("Items List"));
        assertEquals("Rental header should have a label", true, hasLabel);
        // Then
        long countField = output.get(1).chars().filter(ch -> ch == '|').count() + 1;
        assertEquals("Rental header should have 2 field", 2, countField);

        // Item
        // Given
        Rental item = new Rental() {};
        // When
        output = ItemPrinter.item(item, false);
        // Then
        countField = output.get(0).chars().filter(ch -> ch == '|').count() + 1;
        assertEquals("Rental item should have 2 field", 2, countField);

        // Collection
        // When
        output = ItemPrinter.collection(collection, Rental.class, false);
        // Then
        boolean hasBookLabel = output.stream().anyMatch(text -> text.contains("Books List"));
        assertEquals("Rental collection should have book header", true, hasBookLabel);
        // Then
        boolean hasMovieLabel = output.stream().anyMatch(text -> text.contains("Movies List"));
        assertEquals("Rental collection should have movie header", true, hasMovieLabel);
        // Then
        int countLine = output.size();
        assertEquals("Rental collection should have 6 line", 6, countLine);
    }

    @Test
    public void bookTest() {
        ArrayList<String> output;

        // Header
        // When
        output = ItemPrinter.header(Book.class, false);
        // Then
        boolean hasLabel = output.stream().anyMatch(text -> text.contains("Books List"));
        assertEquals("Book header should have a label", true, hasLabel);
        // Then
        long countField = output.get(1).chars().filter(ch -> ch == '|').count() + 1;
        assertEquals("Book header should have 3 field", 3, countField);

        // Item
        // Given
        Book item = new Book("Book");
        // When
        output = ItemPrinter.item(item, false);
        // Then
        countField = output.get(0).chars().filter(ch -> ch == '|').count() + 1;
        assertEquals("Book item should have 3 field", 3, countField);

        // Collection
        // When
        output = ItemPrinter.collection(collection, Book.class, false);
        // Then
        boolean hasBookLabel = output.stream().anyMatch(text -> text.contains("Books List"));
        assertEquals("Book collection should have book header", true, hasBookLabel);
        // Then
        int countLine = output.size();
        assertEquals("Book collection should have 3 line", 3, countLine);
    }

    @Test
    public void movieTest() {
        ArrayList<String> output;

        // Header
        // When
        output = ItemPrinter.header(Movie.class, false);
        // Then
        boolean hasLabel = output.stream().anyMatch(text -> text.contains("Movies List"));
        assertEquals("Movie header should have a label", true, hasLabel);
        // Then
        long countField = output.get(1).chars().filter(ch -> ch == '|').count() + 1;
        assertEquals("Movie header should have 4 field", 4, countField);

        // Item
        // Given
        Movie item = new Movie("movie");
        // When
        output = ItemPrinter.item(item, false);
        // Then
        countField = output.get(0).chars().filter(ch -> ch == '|').count() + 1;
        assertEquals("Movie item should have 4 field", 4, countField);

        // Collection
        // When
        output = ItemPrinter.collection(collection, Movie.class, false);
        // Then
        boolean hasMovieLabel = output.stream().anyMatch(text -> text.contains("Movies List"));
        assertEquals("Movie collection should have movie header", true, hasMovieLabel);
        // Then
        int countLine = output.size();
        assertEquals("Movie collection should have 3 line", 3, countLine);
    }
}
