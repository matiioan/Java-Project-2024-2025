package api;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdminsTest {

    private Admins admin;

    @Before
    public void setUp() {
        admin = new Admins("admin", "admin123");
    }

    @Test
    public void testGetUsername() {
        assertEquals("admin", admin.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("admin123", admin.getPassword());
    }

    @Test
    public void testProductPlacement() {
        String title = "Test Product";
        String description = "Description of the test product";
        String category = "Electronics";
        String subCategory = "Mobile";
        double price = 199.99;
        double quantity = 10;
        boolean productExists = admin.isProductExists(title);
        admin.productPlacement(title, description, category, subCategory, price, quantity);
        productExists = admin.isProductExists(title);
        assertTrue("Το προϊόν πρέπει να υπάρχει μετά την τοποθέτηση", productExists);
    }

    @Test
    public void testIsProductExists() {
        String title = "Existing Product";
        admin.productPlacement(title, "Description", "Category", "SubCategory", 100.0, 5);
        boolean exists = admin.isProductExists(title);
        assertTrue("Το προϊόν πρέπει να υπάρχει", exists);
        exists = admin.isProductExists("Non Existing Product");
        assertFalse("Το προϊόν δεν πρέπει να υπάρχει", exists);
    }
}
