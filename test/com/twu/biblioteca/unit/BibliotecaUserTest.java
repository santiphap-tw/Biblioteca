package com.twu.biblioteca.unit;

import com.twu.biblioteca.BibliotecaUser;
import com.twu.biblioteca.model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BibliotecaUserTest {


    private BibliotecaUser bibliotecaUser;

    @Before
    public void initialize() {
        // Given...
        bibliotecaUser = new BibliotecaUser();
    }

    @Test
    public void getUsersTest() {
        // Getter method
    }

    @Test
    public void getCurrentUserTest() {
        // Getter method
    }

    @Test
    public void loginTest() {
        // Positive Test
        // When
        User user = bibliotecaUser.login("111-1111", "1111");
        // Then
        assertEquals("login should return user 111-1111", "111-1111", user.getId());
        User currentUser = bibliotecaUser.getCurrentUser();
        assertEquals("Current user should same as the logged in user", user, currentUser);

        // Negative Test
        // When
        user = bibliotecaUser.login("xxx-xxxx", "xxxx");
        // Then
        assertEquals("login should return null", null, user);
        currentUser = bibliotecaUser.getCurrentUser();
        assertEquals("Current user should be null", null, currentUser);
    }

    @Test
    public void logoutTest() {
        // Positive Test
        // Given
        User user = bibliotecaUser.login("111-1111", "1111");
        // When
        User loggedOutUser = bibliotecaUser.logout();
        // Then
        assertEquals("logout should return user 111-1111", "111-1111", loggedOutUser.getId());
        User currentUser = bibliotecaUser.getCurrentUser();
        assertEquals("Current user should be null", null, currentUser);

        // Negative Test
        // Given
        bibliotecaUser.login("111-1111", "1111");
        bibliotecaUser.logout();
        // When
        loggedOutUser = bibliotecaUser.logout();
        // Then
        assertEquals("logout should return null if no current user", null, loggedOutUser);
        currentUser = bibliotecaUser.getCurrentUser();
        assertEquals("Current user should be null", null, currentUser);
    }
}
