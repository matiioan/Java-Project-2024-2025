package api;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomersTest {

    private Customers customer;

    @Before
    public void setUp() {
        customer = new Customers("testUser", "testPassword", "John", "Doe");
    }

    @Test
    public void getName() {
        assertEquals("John", customer.getName());
    }

    @Test
    public void getSurname() {
        assertEquals("Doe", customer.getSurname());
    }

    @Test
    public void getUsername() {
        assertEquals("testUser", customer.getUsername());
    }

    @Test
    public void getPassword() {
        assertEquals("testPassword", customer.getPassword());
    }

    @Test
    public void setName() {
        customer.setName("Michael");
        assertEquals("Michael", customer.getName());
    }

    @Test
    public void setSurname() {
        customer.setSurname("Jordan");
        assertEquals("Jordan", customer.getSurname());
    }
}