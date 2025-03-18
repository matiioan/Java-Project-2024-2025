package api;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest {

    @Test
    public void setOriginalPrice() {
        Product product = new Product();
        product.setOriginalPrice("15,99€");
        assertEquals("15,99€", product.getOriginalPrice());
    }

    @Test
    public void getOriginalPrice() {
        Product product = new Product();
        product.setOriginalPrice("12,50€");
        assertEquals("12,50€", product.getOriginalPrice());
    }

    @Test
    public void getQuantity() {
        Product product = new Product("Μήλα", "Κόκκινα μήλα από Πήλιο", "Φρούτα", "Μήλα", 2.99, 5.0);
        assertEquals(5.0, product.getQuantity(), 0.01);
    }

    @Test
    public void getSubCategory() {
        Product product = new Product("Μήλα", "Κόκκινα μήλα από Πήλιο", "Φρούτα", "Μήλα", 2.99, 5.0);
        assertEquals("Μήλα", product.getSubCategory());
    }

    @Test
    public void setQuantity() {
        Product product = new Product();
        product.setQuantity(10.0);
        assertEquals(10.0, product.getQuantity(), 0.01);
    }

    @Test
    public void getTitle() {
        Product product = new Product("Πορτοκάλια", "Φρέσκα πορτοκάλια Λακωνίας", "Φρούτα", "Εσπεριδοειδή", 3.50, 6.0);
        assertEquals("Πορτοκάλια", product.getTitle());
    }

    @Test
    public void getDescription() {
        Product product = new Product("Καφές", "Ελληνικός καφές, μέτριος", "Ροφήματα", "Καφέδες", 4.50, 1.0);
        assertEquals("Ελληνικός καφές, μέτριος", product.getDescription());
    }

    @Test
    public void getCategory() {
        Product product = new Product("Καφές", "Ελληνικός καφές, μέτριος", "Ροφήματα", "Καφέδες", 4.50, 1.0);
        assertEquals("Ροφήματα", product.getCategory());
    }

    @Test
    public void getPrice() {
        Product product = new Product("Σοκολάτα", "Μαύρη σοκολάτα 70%", "Γλυκά", "Σοκολάτες", 2.80, 3.0);
        assertEquals(2.80, product.getPrice(), 0.01);
    }

    @Test
    public void setTitle() {
        Product product = new Product();
        product.setTitle("Γάλα");
        assertEquals("Γάλα", product.getTitle());
    }

    @Test
    public void setDescription() {
        Product product = new Product();
        product.setDescription("Γάλα φρέσκο, πλήρες");
        assertEquals("Γάλα φρέσκο, πλήρες", product.getDescription());
    }

    @Test
    public void setCategory() {
        Product product = new Product();
        product.setCategory("Γαλακτοκομικά");
        assertEquals("Γαλακτοκομικά", product.getCategory());
    }

    @Test
    public void setPrice() {
        Product product = new Product();
        product.setPrice(1.50);
        assertEquals(1.50, product.getPrice(), 0.01);
    }

    @Test
    public void setSubcategory() {
        Product product = new Product();
        product.setSubcategory("Πλήρες Γάλα");
        assertEquals("Πλήρες Γάλα", product.getSubCategory());
    }

    @Test
    public void getErrorMessage() {
        Product product = new Product();
        assertEquals("Ο τίτλος είναι υποχρεωτικός.", product.getErrorMessage());

        product.setTitle("Μήλα");
        assertEquals("Η κατηγορία είναι υποχρεωτική.", product.getErrorMessage());

        product.setCategory("Φρούτα");
        assertEquals("Η περιγραφή είναι υποχρεωτική.", product.getErrorMessage());

        product.setDescription("Κόκκινα μήλα από Πήλιο");
        assertEquals("Η τιμή πρέπει να είναι θετικός αριθμός.", product.getErrorMessage());

        product.setPrice(3.50);
        assertEquals("Η υποκατηγορία είναι υποχρεωτική.", product.getErrorMessage());

        product.setSubcategory("Μήλα");
        assertEquals("Η ποσότητα πρέπει να είναι θετικός αριθμός.", product.getErrorMessage());

        product.setQuantity(5.0);
        assertEquals("", product.getErrorMessage());
    }


    @Test
    public void testToString() {
        Product product = new Product("Μήλα", "Κόκκινα μήλα από Πήλιο", "Φρούτα", "Μήλα", 2.99, 5.0);
        String expected = " Το προϊόν: Μήλα ,με περιγραφή: Κόκκινα μήλα από Πήλιο,ανήκει στην κατηγορία: Φρούτα,και στην υποκατηγορία: Μήλα,και έχει τιμή: 2.99€" +
                ",Ποσοτητα του προϊόντος: 5.0";
        assertEquals(expected, product.toString());
    }

}