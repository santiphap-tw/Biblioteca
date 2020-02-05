package com.twu.biblioteca.unit;

import com.twu.biblioteca.BibliotecaUser;
import com.twu.biblioteca.database.UserDatabase;
import com.twu.biblioteca.model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BibliotecaUserTest {


    private BibliotecaUser bibliotecaUser;
    private User sampleUser;

    @Before
    public void initialize() {
        // Given
        bibliotecaUser = new BibliotecaUser();
        sampleUser = UserDatabase.getInstance().getUsers().get(0);
    }

    @Test
    public void shouldLoginCorrectUser() {
        // Given
        // When
        User user = bibliotecaUser.login(sampleUser.getId(), sampleUser.getPassword());
        User currentUser = bibliotecaUser.getCurrentUser();
        // Then
        assertEquals("login should return user " + sampleUser.getId(), sampleUser.getId(), user.getId());
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
        bibliotecaUser.login(sampleUser.getId(), sampleUser.getPassword());
        // When
        User loggedOutUser = bibliotecaUser.logout();
        User currentUser = bibliotecaUser.getCurrentUser();
        // Then
        assertEquals("logout should return user " + sampleUser.getId(), sampleUser.getId(), loggedOutUser.getId());
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
