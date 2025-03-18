package api;

import java.io.*;
import java.util.*;

/**
 * Η κλάση ProductManager είναι υπεύθυνη για τη διαχείριση των προϊόντων,
 * περιλαμβάνοντας τη φόρτωση, αποθήκευση και ενημέρωση των προϊόντων από και προς το αρχείο.
 */
public class ProductManager {

    // Το αρχείο που αποθηκεύει τα προϊόντα
    public File productsFile = new File("src/files/products.txt");

    /**
     * Αυτή η μέθοδος φορτώνει ένα προϊόν από το αρχείο, βασισμένο στον τίτλο του.
     * Αν το προϊόν βρεθεί, επιστρέφει το προϊόν, αλλιώς επιστρέφει null.
     *
     * @param productTitle ο τίτλος του προϊόντος που αναζητούμε.
     * @return το προϊόν με τον συγκεκριμένο τίτλο, ή null αν δεν βρεθεί.
     */
    public Product loadProduct(String productTitle) {
        try (BufferedReader reader = new BufferedReader(new FileReader(productsFile))) {
            String line;
            String title = null, description = null, category = null, subCategory = null;
            double price = 0.0, quantity = 0.0;
            boolean productFound = false;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!productFound) {
                    if (line.startsWith("Τίτλος: ") && line.substring(7).trim().equalsIgnoreCase(productTitle)) {
                        title = line.substring(7).trim();
                        productFound = true;
                    }
                } else {
                    if (line.startsWith("Περιγραφή:")) {
                        description = line.substring(11).trim();
                    } else if (line.startsWith("Κατηγορία:")) {
                        category = line.substring(10).trim();
                    } else if (line.startsWith("Υποκατηγορία:")) {
                        subCategory = line.substring(13).trim();
                    } else if (line.startsWith("Τιμή:")) {
                        String priceStr = line.substring(5).trim().replace("€", "").replace(",", ".");
                        price = Double.parseDouble(priceStr);
                    } else if (line.startsWith("Ποσότητα:")) {
                        String quantityStr = line.substring(9).trim().replace("kg", "").replace("τεμάχια", "").trim();
                        quantity = Double.parseDouble(quantityStr);
                    }
                    if (line.isEmpty() || !reader.ready()) {
                        if (title != null && description != null && category != null && subCategory != null) {
                            return new Product(title, description, category, subCategory, price, quantity);
                        }
                    }
                }
            }
            if (!productFound) {
                System.out.println("Το προϊόν με τον τίτλο '" + productTitle + "' δεν βρέθηκε.");
            }
        } catch (IOException e) {
            System.out.println("Σφάλμα κατά την ανάγνωση του αρχείου: " + e.getMessage());
        }
        return null;
    }

    /**
     * Αυτή η μέθοδος αποθηκεύει ή ενημερώνει ένα προϊόν στο αρχείο με βάση το παλιό του τίτλο.
     * Αν το προϊόν με τον παλιό τίτλο υπάρχει, αντικαθιστά τα δεδομένα του με τα νέα δεδομένα του προϊόντος.
     *
     * @param product το προϊόν που θα αποθηκευτεί στο αρχείο.
     * @param oldTitle ο παλιός τίτλος του προϊόντος που θα αντικατασταθεί.
     */
    public void saveProduct(Product product, String oldTitle) {
        List<String> fileLines = new ArrayList<>();
        boolean productFound = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(productsFile))) {
            String line;
            StringBuilder currentProductData = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("Τίτλος:")) {
                    if (productFound) {
                        fileLines.add(currentProductData.toString());
                        currentProductData.setLength(0);
                        productFound = false;
                    }
                    if (line.substring(7).trim().equalsIgnoreCase(oldTitle)) {
                        productFound = true;
                        currentProductData.append("Τίτλος: ").append(product.getTitle()).append("\n")
                                .append("Περιγραφή: ").append(product.getDescription()).append("\n")
                                .append("Κατηγορία: ").append(product.getCategory()).append("\n")
                                .append("Υποκατηγορία: ").append(product.getSubCategory()).append("\n")
                                .append("Τιμή: ").append(String.format("%.2f", product.getPrice()).replace(".", ",")).append("€\n")
                                .append("Ποσότητα: ").append(product.getQuantity()).append(" τεμάχια\n\n");

                        continue;
                    }
                }
                if (!productFound) {
                    fileLines.add(line);
                }
            }
            if (productFound) {
                fileLines.add(currentProductData.toString());
            }
        } catch (IOException e) {
            System.out.println("Σφάλμα κατά την ανάγνωση του αρχείου: " + e.getMessage());
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(productsFile))) {
            for (String fileLine : fileLines) {
                writer.write(fileLine);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Σφάλμα κατά την εγγραφή στο αρχείο: " + e.getMessage());
        }
    }

}