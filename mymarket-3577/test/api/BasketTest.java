package api;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BasketTest {

    private Basket basket;
    private Product product;

    @Before
    public void setUp() {
        basket = new Basket();
        product = new Product();
        product.setTitle("Test Product");
        product.setPrice(10.0);
        product.setQuantity(100);
    }

    @Test
    public void addProductToBasket() {
        basket.AddProductToBasket(product, 2);
        assertEquals(1, basket.prTitle.size());
        assertEquals("Test Product", basket.prTitle.get(0));
        assertEquals(2.0, basket.prQuantity.get(0), 0.01);
        assertEquals(20.0, basket.prValue.get(0), 0.01);
        assertEquals(98, product.getQuantity(), 0.01);
    }

    @Test
    public void removeProductFromBasket() {
        basket.AddProductToBasket(product, 2);
        basket.RemoveProductFromBasket(basket, 0);
        assertEquals(0, basket.prTitle.size());
        assertEquals(100, product.getQuantity(), 0.01);
    }

    @Test
    public void totalPrice() {
        basket.AddProductToBasket(product, 2);
        Product product2 = new Product();
        product2.setTitle("Second Product");
        product2.setPrice(5.0);
        product2.setQuantity(50);
        basket.AddProductToBasket(product2, 3);
        assertEquals(31.0, basket.TotalPrice(), 0.01);
    }

    @Test
    public void orderToHistory() {
        basket.AddProductToBasket(product, 2);
        Users user = new Users("testUser", "password");
        basket.OrderToHistory(user, basket.TotalPrice());
        assertNotNull(basket);
    }
}
