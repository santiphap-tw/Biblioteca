package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.Formatter;
import com.twu.biblioteca.cli.operation.MyBorrowOperation;
import com.twu.biblioteca.database.RentalDatabase;
import com.twu.biblioteca.database.UserDatabase;
import com.twu.biblioteca.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MyBorrowOperationTest {

    private User sampleUser1 = UserDatabase.getInstance().getUsers().get(0);
    private Rental sampleItem1 = RentalDatabase.getInstance().getItems(item -> item.getClass() == Book.class).get(0);
    private Rental sampleItem2 = RentalDatabase.getInstance().getItems(item -> item.getClass() == Movie.class).get(0);
    private Rental sampleItem3 = RentalDatabase.getInstance().getItems(item -> item.getClass() == Book.class).get(1);

    private MyBorrowOperation myBorrowOperation;
    private ArrayList<String> expectedOutput;

    @Before
    public void initialize(){
        // Given
        Biblioteca.getInstance().initialize();
        myBorrowOperation = new MyBorrowOperation("");
        Biblioteca.getInstance().user().login(sampleUser1.getId(), sampleUser1.getPassword());
        Biblioteca.getInstance().doCheckOut(sampleItem1.getTitle());
        Biblioteca.getInstance().doCheckOut(sampleItem2.getTitle());
        Biblioteca.getInstance().doCheckOut(sampleItem3.getTitle());
        ArrayList<Rental> collection = Biblioteca.getInstance().user().getCurrentUser().getItems();
        expectedOutput = Formatter.items(collection, Rental.class, false);
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
        Biblioteca.getInstance().user().logout();
        // When
        ArrayList<String> output = myBorrowOperation.run("");
        // Then
        boolean isFail = output.stream().anyMatch(text -> text.equals(Label.MY_INFO_FAIL.text));
        assertEquals("borrowing should be fail", true, isFail);
    }
}
