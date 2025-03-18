package api;

public class Product
{
    /**
     * Αυτή η Κλάση αναπαριστά ένα προϊόν και αποθηκεύει για αυτό τον τίτλο του, μια περιγραφή για αυτό,
     * τις υποκατηγορίες του, την τιμή του και τέλος την ποσότητα (κιλά/τεμάχια) που διατίθεται στο κατάστημα.
     */

    private String title;
    private String description;
    private String category;
    private String subCategory;
    private double price;
    private double quantity;
    private String originalPrice;

    /**
     * Κατασκευαστής της κλάσης Product
     * @param title ο τίτλος του προϊόντος.
     * @param description η περιγραφή του προϊόντος.
     * @param category η κατηγορία στην οποία ανήκει το προϊόν.
     * @param subCategory η υποκατηγορία στην οποία ανήκει το προϊόν.
     * @param price η τιμή του προϊόντος.
     * @param quantity η ποσότητα του προϊόντος.
     */
    public Product(String title, String description, String category, String subCategory, double price, double quantity)
    {
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.subCategory = subCategory;
        if (title.toLowerCase().contains("kg"))
        {
            this.quantity = quantity;
        }
        else
        {
            this.quantity = (int)quantity;
        }
    }

    /**
     * Κατασκευαστής χωρίς ορίσματα για την κλάση Product.
     * Χρησιμοποιείται για την δημιουργία ενός προϊόντος χωρίς αρχικές τιμές.
     */
    public Product()
    {
    }

    /**
     * Η μέθοδος αυτή ορίζει την αρχική τιμή του προϊόντος.
     * @param originalPrice η  αρχική τιμή του προϊόντος πριν από πιθανές εκπτώσεις.
     */
    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    /**
     * Η μέθοδος αυτή επιστρέφει την αρχική τιμή του προϊόντος.
     * @return η αρχική τιμή του προϊόντος.
     */
    public String getOriginalPrice() {
        return this.originalPrice;
    }

    /**
     * Η μέθοδος αυτή επιστρέφει την ποσότητα του προϊόντος (τύπου κιλού).
     * @return η ποσότητα του προϊόντος σε κιλά ή τεμάχια.
     */
    public Double getQuantity() {
        return quantity;
    }

    /**
     * Η μέθοδος αυτή επιστρέφει την υποκατηγορία του προϊόντος.
     * @return η υποκατηγορία του προϊόντος.
     */
    public String getSubCategory()
    {
        return subCategory;
    }

    /**
     * Η μέθοδος αυτή δέχεται ως όρισμα μία πραγματική τιμή που αντικαθιστά την ποσότητα του προϊόντος.
     * @param quantity η νέα ποσότητα του προϊόντος.
     */
    public void setQuantity(double quantity)
    {
        this.quantity = quantity;
    }

    /**
     * Η μέθοδος αυτή επιστρέφει τον τίτλο του προϊόντος.
     * @return ο τίτλος του προϊόντος.
     */
    public String getTitle()
    {
        return this.title;
    }

    /**
     * Η μέθοδος αυτή επιστρέφει μια περιγραφή του προϊόντος.
     * @return η περιγραφή του προϊόντος.
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Η μέθοδος αυτή επιστρέφει την κατηγορία του προϊόντος.
     * @return η κατηγορία του προϊόντος.
     */
    public String getCategory()
    {
        return this.category;
    }

    /**
     * Η μέθοδος αυτή επιστρέφει την τιμή του προϊόντος.
     * @return η τιμή του προϊόντος.
     */
    public double getPrice()
    {
        return this.price;
    }

    /**
     * Η μέθοδος αυτή δέχεται ως όρισμα μία συμβολοσειρά που αντικαθιστά τον τίτλο του υπάρχοντος προϊόντος.
     * @param title ο νέος τίτλος του προϊόντος.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Η μέθοδος αυτή δέχεται ως όρισμα μία συμβολοσειρά που αντικαθιστά την περιγραφή του υπάρχοντος προϊόντος.
     * @param description η νέα περιγραφή του προϊόντος.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Η μέθοδος αυτή δέχεται ως όρισμα μία συμβολοσειρά που αντικαθιστά την κατηγορία του υπάρχοντος προϊόντος.
     * @param category η νέα κατηγορία του προϊόντος.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Η μέθοδος αυτή δέχεται ως όρισμα μία ακέραια τιμή που αντικαθιστά την τιμή του υπάρχοντος προϊόντος.
     * @param price η νέα τιμή του προϊόντος.
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * Η μέθοδος αυτή δέχεται ως όρισμα μία συμβολοσειρά που αντικαθιστά την υποκατηγορία του υπάρχοντος προϊόντος.
     * @param subCategory η νέα υποκατηγορία του προϊόντος.
     */
    public void setSubcategory(String subCategory)
    {
        this.subCategory = subCategory;
    }

    /**
     * Μηνύματα σε περίπτωση που δοθεί λανθασμένη τιμή σε κάποιο πεδίο.
     * @return το μήνυμα λάθους που αντιστοιχεί στο λάθος πεδίο.
     */
    public String getErrorMessage() {
        if (title.isEmpty()) return "Ο τίτλος είναι υποχρεωτικός.";
        if (category.isEmpty()) return "Η κατηγορία είναι υποχρεωτική.";
        if(description.isEmpty()) return "Η περιγραφή είναι υποχρεωτική.";
        if (price <= 0) return "Η τιμή πρέπει να είναι θετικός αριθμός.";
        if(subCategory.isEmpty()) return "Η υποκατηγορία είναι υποχρεωτική.";
        if(quantity < 0) return "Η ποσότητα πρέπει να είναι θετικός αριθμός.";
        return "";
    }

    /**
     * Αυτή η μέθοδος επιστρέφει τα χαρακτηριστικά ενός προϊόντος.
     * @return τα χαρακτηριστικά του προϊόντος σε μορφή συμβολοσειράς.
     */
    public String toString() {
        return "Το προϊόν: " + title + ", με περιγραφή: " + description + ", ανήκει στην κατηγορία: " + category + ", και στην υποκατηγορία: " + subCategory +
                ", και έχει τιμή: " + price + "€" + ", Ποσότητα του προϊόντος: " + quantity;
    }

}
