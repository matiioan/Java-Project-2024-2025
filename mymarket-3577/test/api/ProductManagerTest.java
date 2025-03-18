package api;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.*;
import java.nio.file.Files;


public class ProductManagerTest {

    private ProductManager productManager;
    private File productsFile;
    private Product testProduct;

    @Before
    public void setUp() throws Exception {
        productManager = new ProductManager();
        productsFile = new File("src/files/products.txt");
        testProduct = new Product("TestProduct", "A test product", "Category1", "SubCategory1", 20.5, 10);
        if (productsFile.exists()) {
            Files.delete(productsFile.toPath());
        }
        Files.createFile(productsFile.toPath());
    }

    @Test
    public void loadProduct() {
        productManager.saveProduct(testProduct, "");
        Product loadedProduct = productManager.loadProduct("TestProduct");
        assertNotNull("Το προϊόν δεν φορτώθηκε", loadedProduct);
        assertEquals("Ο τίτλος του προϊόντος δεν είναι σωστός", "TestProduct", loadedProduct.getTitle());
        assertEquals("Η περιγραφή του προϊόντος δεν είναι σωστή", "A test product", loadedProduct.getDescription());
        assertEquals("Η κατηγορία του προϊόντος δεν είναι σωστή", "Category1", loadedProduct.getCategory());
        assertEquals("Η υποκατηγορία του προϊόντος δεν είναι σωστή", "SubCategory1", loadedProduct.getSubCategory());
        assertEquals("Η τιμή του προϊόντος δεν είναι σωστή", 20.5, loadedProduct.getPrice(), 0.01);
        assertEquals("Η ποσότητα του προϊόντος δεν είναι σωστή", 10, loadedProduct.getQuantity(), 0.01);
    }

    @Test
    public void saveProduct() {
        productManager.saveProduct(testProduct, "");
        try (BufferedReader reader = new BufferedReader(new FileReader(productsFile))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Τίτλος: TestProduct")) {
                    found = true;
                    break;
                }
            }
            assertTrue("Το προϊόν δεν αποθηκεύτηκε σωστά στο αρχείο", found);
        } catch (IOException e) {
            fail("Σφάλμα κατά την ανάγνωση του αρχείου: " + e.getMessage());
        }
    }

    @Test
    public void saveProductUpdateExisting() {
        productManager.saveProduct(testProduct, "");
        Product updatedProduct = new Product("TestProduct", "Updated test product", "Category2", "SubCategory2", 25.0, 15);
        productManager.saveProduct(updatedProduct, "TestProduct");
        Product loadedUpdatedProduct = productManager.loadProduct("TestProduct");
        assertNotNull("Το ενημερωμένο προϊόν δεν φορτώθηκε", loadedUpdatedProduct);
        assertEquals("Η τιμή του προϊόντος δεν ενημερώθηκε σωστά", 25.0, loadedUpdatedProduct.getPrice(), 0.01);
        assertEquals("Η ποσότητα του προϊόντος δεν ενημερώθηκε σωστά", 15, loadedUpdatedProduct.getQuantity(), 0.01);
        assertEquals("Η περιγραφή του προϊόντος δεν ενημερώθηκε σωστά", "Updated test product", loadedUpdatedProduct.getDescription());
    }
}
