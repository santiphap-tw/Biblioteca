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
        // Given
        bibliotecaUser = new BibliotecaUser();
    }

    @Test
    public void shouldLoginCorrectUser() {
        // Given
        // When
        User user = bibliotecaUser.login("111-1111", "1111");
        User currentUser = bibliotecaUser.getCurrentUser();
        // Then
        assertEquals("login should return user 111-1111", "111-1111", user.getId());
        assertEquals("Current user should same as the logged in user", user, currentUser);
    }

    @Test
    public void shouldNotLoginWrongUser() {
        // Given
        // When
        User user = bibliotecaUser.login("xxx-xxxx", "xxxx");
        User currentUser = bibliotecaUser.getCurrentUser();
        // Then
        assertEquals("login should return null", null, user);
        assertEquals("Current user should be null", null, currentUser);
    }

    @Test
    public void shouldLogoutWhenHaveUser() {
        // Given
        bibliotecaUser.login("111-1111", "1111");
        // When
        User loggedOutUser = bibliotecaUser.logout();
        User currentUser = bibliotecaUser.getCurrentUser();
        // Then
        assertEquals("logout should return user 111-1111", "111-1111", loggedOutUser.getId());
        assertEquals("Current user should be null", null, currentUser);
    }


    @Test
    public void shouldNotLogoutWhenNoUser() {
        // Given
        // When
        User loggedOutUser = bibliotecaUser.logout();
        User currentUser = bibliotecaUser.getCurrentUser();
        // Then
        assertEquals("logout should return null if no current user", null, loggedOutUser);
        assertEquals("Current user should be null", null, currentUser);
    }
}
