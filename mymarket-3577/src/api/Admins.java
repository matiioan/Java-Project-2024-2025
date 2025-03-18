package api;

import java.io.*;

/**
 * Η κλάση Admins κληρονομεί από την κλάση Users και αναπαριστά τους χρήστες με δικαιώματα διαχειριστή.
 * Αυτή η κλάση περιλαμβάνει λειτουργίες για την τοποθέτηση νέων προϊόντων και την αναζήτηση προϊόντων στο κατάστημα.
 */
public class Admins extends Users {

    /**
     * Κατασκευαστής της κλάσης Admins.
     * Αυτή η μέθοδος καλεί τον κατασκευαστή της κλάσης Users για να δημιουργήσει έναν χρήστη με όνομα χρήστη και κωδικό πρόσβασης.
     *
     * @param username το όνομα χρήστη του διαχειριστή.
     * @param password ο κωδικός πρόσβασης του διαχειριστή.
     */
    public Admins(String username, String password ) {
        super(username, password);
    }

    /**
     * Σε αυτή τη μέθοδο δεχόμαστε τις παραμέτρους ενός προϊόντος και το τοποθετούμε στο τέλος του αρχείου προϊόντων.
     * Εάν το προϊόν δεν υπάρχει ήδη, προστίθεται στο αρχείο, αλλιώς εκτυπώνεται μήνυμα ότι το προϊόν υπάρχει ήδη.
     *
     * @param title ο τίτλος του προϊόντος.
     * @param description η περιγραφή του προϊόντος.
     * @param category η κατηγορία του προϊόντος.
     * @param subCategory η υποκατηγορία του προϊόντος.
     * @param price η τιμή του προϊόντος.
     * @param quantity η ποσότητα του προϊόντος.
     */
    public void productPlacement (String title, String description, String category, String subCategory, double price, double quantity) {
        try (BufferedWriter productWriter = new BufferedWriter(new FileWriter(productsFile, true))) {
            productWriter.write("\n\nΤίτλος: " + title);
            productWriter.write("\nΠεριγραφή: " + description);
            productWriter.write("\nΚατηγορία: " + category);
            productWriter.write("\nΥποκατηγορία: " + subCategory);
            productWriter.write("\nΤιμή: " + price);
            productWriter.write("\nΠοσότητα: " + quantity);
            System.out.println("Το προϊόν έχει ανέβει με επιτυχία");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Αυτή η μέθοδος ελέγχει αν υπάρχει ήδη ένα προϊόν με τον ίδιο τίτλο στο αρχείο προϊόντων.
     * Αν το προϊόν υπάρχει, επιστρέφει true, αλλιώς επιστρέφει false.
     *
     * @param title ο τίτλος του προϊόντος που θέλουμε να ελέγξουμε.
     * @return true αν το προϊόν υπάρχει, αλλιώς false.
     */
    public boolean isProductExists(String title) {
        try (BufferedReader reader = new BufferedReader(new FileReader(productsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Τίτλος:") && line.contains(title)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
