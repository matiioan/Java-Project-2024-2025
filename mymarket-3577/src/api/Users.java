package api;

import java.io.*;

/**
 * Η κλάση Users διαχειρίζεται τους χρήστες του συστήματος.
 * Περιλαμβάνει τη δυνατότητα δημιουργίας νέων χρηστών, αναζήτησης χρηστών με βάση το όνομα χρήστη και τον κωδικό,
 * καθώς και τον καθορισμό του ρόλου του χρήστη.
 */
public class Users {

    protected int roleID = 0;
    protected String username;
    protected String password;
    protected boolean usernameFound;
    protected boolean userFound;
    public int scenarios = 0;
    static File userFile = new File("src/files/users.txt");
    public File productsFile = new File("src/files/products.txt");

    /**
     * Ο constructor χωρίς παραμέτρους δημιουργεί έναν νέο χρήστη  χωρίς να αρχικοποιεί δεδομένα.
     */
    public Users() {
    }

    /**
     * Ο constructor που δέχεται όνομα χρήστη και κωδικό πρόσβασης για την αρχικοποίηση του χρήστη.
     *
     * @param username το όνομα χρήστη του χρήστη.
     * @param password ο κωδικός πρόσβασης του χρήστη.
     */
    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Επιστρέφει τον κωδικό ρόλου του χρήστη.
     *
     * @return ο κωδικός ρόλου του χρήστη.
     */
    public int getRoleID() {
        return roleID;
    }

    /**
     * Επιστρέφει το όνομα χρήστη του χρήστη.
     *
     * @return το όνομα χρήστη.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Επιστρέφει τον κωδικό πρόσβασης του χρήστη.
     *
     * @return ο κωδικός πρόσβασης.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Ανοίγει το αρχείο χρηστών για να ελέγξει αν υπάρχει ο χρήστης με τα δεδομένα username και password.
     *
     * @param username το όνομα χρήστη που θα αναζητηθεί.
     * @param password ο κωδικός πρόσβασης που θα αναζητηθεί.
     */
    public void openFile(String username, String password) {
        readUserFile(username, password);
    }

    /**
     * Διαβάζει το αρχείο χρηστών και ελέγχει αν υπάρχει ο χρήστης με το όνομα χρήστη και τον κωδικό πρόσβασης.
     * Ενημερώνει τις σημαίες `usernameFound` και `userFound` αναλόγως.
     *
     * @param username το όνομα χρήστη που θα αναζητηθεί.
     * @param password ο κωδικός πρόσβασης που θα αναζητηθεί.
     */
    private void readUserFile(String username, String password) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails.length >= 3) {
                    String fileUsername = userDetails[0].trim();
                    String filePassword = userDetails[1].trim();
                    int fileRoleID = Integer.parseInt(userDetails[2].trim());
                    if (fileUsername.equals(username)) {
                        usernameFound = true;
                        if (filePassword.equals(password)) {
                            userFound = true;
                            roleID = fileRoleID;
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Σφάλμα κατά την ανάγνωση του αρχείου: " + e.getMessage());
        }
    }

    /**
     * Ελέγχει αν υπάρχει ο χρήστης με το όνομα χρήστη και τον κωδικό πρόσβασης.
     * Αν βρεθεί ο χρήστης, επιστρέφει true, αλλιώς επιστρέφει false και ενημερώνει την κατάσταση στο `scenarios`.
     *
     * @param username το όνομα χρήστη που θα αναζητηθεί.
     * @param password ο κωδικός πρόσβασης που θα αναζητηθεί.
     * @return true αν βρέθηκε ο χρήστης, αλλιώς false.
     */
    public Boolean findUser(String username, String password) {
        openFile(username, password);
        if (!userFound) {
            scenarios = usernameFound ? 1 : 2;
            return false;
        } else {
            scenarios = 3;
            return true;
        }
    }

    /**
     * Δημιουργεί έναν νέο χρήστη και τον αποθηκεύει στο αρχείο αν δεν υπάρχει ήδη χρήστης με το ίδιο όνομα χρήστη.
     *
     * @param username το όνομα χρήστη για το νέο χρήστη.
     * @param password ο κωδικός πρόσβασης για το νέο χρήστη.
     * @param name το όνομα του νέου χρήστη.
     * @param surname το επώνυμο του νέου χρήστη.
     * @return true αν δημιουργήθηκε επιτυχώς ο χρήστης, αλλιώς false.
     */
    public boolean newUser(String username, String password, String name, String surname) {
        openFile(username, password);
        if (usernameFound) {
            System.out.println("Υπάρχει ήδη χρήστης με το username " + username);
            return false;
        } else {
            try (BufferedWriter userWriter = new BufferedWriter(new FileWriter(userFile, true))) {
                userWriter.write(username + "," + password + ",2," + name + "," + surname + "\n");
                return true;
            } catch (IOException e) {
                System.out.println("Σφάλμα κατά τη δημιουργία του χρήστη: " + e.getMessage());
            }
        }
        return false;
    }
}
