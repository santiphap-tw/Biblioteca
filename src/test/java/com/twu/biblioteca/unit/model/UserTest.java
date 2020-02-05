package com.twu.biblioteca.unit.model;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.database.RentalDatabase;
import com.twu.biblioteca.database.UserDatabase;
import com.twu.biblioteca.model.Rental;
import com.twu.biblioteca.model.User;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void shouldGetItem() {
        // Given
        Biblioteca.getInstance().initialize();
        User user = UserDatabase.getInstance().getUsers().get(0);
        Biblioteca.getInstance().user().login(user.getId(), user.getPassword());
        Biblioteca.getInstance().doCheckOut("Book A");
        Biblioteca.getInstance().doCheckOut("Movie A");
        Biblioteca.getInstance().doCheckOut("Book B");
        // When
        ArrayList<Rental> items = user.getItems();
        // Then
        ArrayList<Rental> expectedItems = RentalDatabase.getInstance().getItems(RentalDatabase.Filter.NOT_AVAILABLE);
        assertEquals("getItems should be sorted", expectedItems, items);
    }
}
