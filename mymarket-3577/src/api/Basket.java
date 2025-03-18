package api;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.time.LocalDate;

public class Basket {
    LocalDate date = LocalDate.now();
    public ArrayList<String> prTitle = new ArrayList<>();
    public ArrayList<Double> prValue = new ArrayList<>();
    public ArrayList<Double> prQuantity = new ArrayList<>();
    static File ordersfile = new File("src/files/orders.txt");

    /**
     * Κατασκευαστής της κλάσης Basket.
     * Δημιουργεί ένα νέο καλάθι για αποθήκευση προϊόντων .
     */
    public Basket() {

    }

    /**
     * Η μέθοδος αυτή προσθέτει ένα προϊόν στο καλάθι με την αντίστοιχη ποσότητα.
     * Υπολογίζει την τιμή του προϊόντος με βάση την ποσότητα και μειώνει την διαθέσιμη ποσότητα του προϊόντος.
     *
     * @param product το προϊόν που προστίθεται στο καλάθι.
     * @param quantity η ποσότητα του προϊόντος που προστίθεται στο καλάθι.
     */
    public void AddProductToBasket(Product product, double quantity) {
        prQuantity.add(quantity);
        prValue.add(product.getPrice() * quantity);
        prTitle.add(product.getTitle());
        product.setQuantity(product.getQuantity() - quantity);
        ProductManager productManager = new ProductManager();
        productManager.saveProduct(product, product.getTitle());
    }

    /**
     * Η μέθοδος αυτή αφαιρεί ένα προϊόν από το καλάθι με βάση την καθορισμένη θέση του.
     * Επαναφέρει την ποσότητα του προϊόντος στο απόθεμα και αφαιρεί το προϊόν από το καλάθι.
     *
     * @param basket το καλάθι από το οποίο αφαιρείται το προϊόν.
     * @param index η θέση του προϊόντος στο καλάθι που αφαιρείται.
     */
    public void RemoveProductFromBasket(Basket basket, int index) {
        ProductManager productManager = new ProductManager();
        Product product = productManager.loadProduct(basket.prTitle.get(index));
        product.setQuantity(product.getQuantity() + prQuantity.get(index));
        productManager.saveProduct(product, product.getTitle());
        basket.prTitle.remove(index);
        basket.prValue.remove(index);
        basket.prQuantity.remove(index);
    }

    /**
     * Η μέθοδος αυτή υπολογίζει τη συνολική τιμή όλων των προϊόντων στο καλάθι.
     *
     * @return η συνολική τιμή των προϊόντων στο καλάθι.
     */
    public double TotalPrice() {
        double totalPrice = 0;
        for (double value: prValue) {
            totalPrice += value;
        }
        return totalPrice;
    }

    /**
     * Η μέθοδος αυτή αποθηκεύει την παραγγελία του χρήστη στο αρχείο ιστορικού παραγγελιών.
     * Καταγράφει τα προϊόντα που παραγγέλθηκαν, την ποσότητα και την τιμή τους, καθώς και τη συνολική τιμή.
     *
     * @param user ο χρήστης που πραγματοποιεί την παραγγελία.
     * @param totalPrice η συνολική τιμή της παραγγελίας.
     */
    public void OrderToHistory(Users user, double totalPrice) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ordersfile, true))) {
            writer.write("User: " + user.getUsername() +"\n" + date);
            writer.newLine();
            for (int i = 0; i < prTitle.size(); i++) {
                String productTitle = prTitle.get(i);
                double productQuantity = prQuantity.get(i);
                double productPrice = prValue.get(i);
                writer.write(String.format("%-30s %-10f %-10.2f \n"+ totalPrice, productTitle, productQuantity, productPrice));
                writer.newLine();
            }
            writer.write("---------------------------");
            writer.newLine();
            prValue.clear();
            prQuantity.clear();
            prTitle.clear();
        } catch (IOException e) {
            System.err.println("Αδυναμία εγγραφής: " + e.getMessage());
        }
    }
}
