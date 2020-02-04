package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.Formatter;
import com.twu.biblioteca.cli.operation.MyBorrowOperation;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.Rental;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MyBorrowOperationTest {

    private Biblioteca biblioteca;
    private MyBorrowOperation myBorrowOperation;
    private ArrayList<String> expectedOutput;

    @Before
    public void initialize(){
        // Given
        biblioteca = new Biblioteca();
        myBorrowOperation = new MyBorrowOperation("", biblioteca);
        biblioteca.user().login("111-1111", "1111");
        biblioteca.doCheckOut("Book A");
        biblioteca.doCheckOut("Movie A");
        biblioteca.doCheckOut("Book B");
        ArrayList<Rental> collection = biblioteca.user().getCurrentUser().getItems();
        expectedOutput = Formatter.collection(collection, Rental.class, false);
    }

    @Test
    public void shouldShowMyBorrowingIfLoggedIn() {
        // Given
        // When
        ArrayList<String> output = myBorrowOperation.run("");
        // Then
        assertEquals("borrowing should return as expected", expectedOutput, output);
    }

    @Test
    public void shouldNotShowMyBorrowingIfNotLoggedIn() {
        // Given
        biblioteca.user().logout();
        // When
        ArrayList<String> output = myBorrowOperation.run("");
        // Then
        boolean isFail = output.stream().anyMatch(text -> text.equals(Label.MY_INFO_FAIL.text));
        assertEquals("borrowing should be fail", true, isFail);
    }
}
