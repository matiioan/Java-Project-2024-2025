package api;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsersTest {

    private Users user;

    @Before
    public void setUp() {
        user = new Users("testUser", "password123");
    }

    @Test
    public void getRoleID() {
        assertEquals(0, user.getRoleID());
    }

    @Test
    public void getUsername() {
        assertEquals("testUser", user.getUsername());
    }

    @Test
    public void getPassword() {
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void openFile() {
        user.openFile("testUser", "password123");
        assertTrue(user.userFound);
    }

    @Test
    public void findUser() {
        boolean userExists = user.findUser("testUser", "password123");
        assertTrue(userExists);
        boolean invalidUser = user.findUser("nonExistentUser", "wrongPassword");
        assertFalse(invalidUser);
    }

    @Test
    public void newUser() {
        boolean newUserCreated = user.newUser("newUser", "newPassword", "John", "Doe");
        assertTrue(newUserCreated);
        boolean userExists = user.newUser("newUser", "anotherPassword", "Jane", "Doe");
        assertFalse(userExists);
    }
}
