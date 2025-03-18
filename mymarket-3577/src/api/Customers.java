package api;

public class Customers extends Users {

    String name;
    String surname;

    /**
     * Κατασκευαστής της κλάσης Customers.
     * Δημιουργεί έναν νέο πελάτη με τα δεδομένα του  χρήστη, το όνομα και το επώνυμο.
     *
     * @param username το όνομα χρήστη του πελάτη.
     * @param password ο κωδικός πρόσβασης του πελάτη.
     * @param name το όνομα του πελάτη.
     * @param surname το επώνυμο του πελάτη.
     */
    public Customers(String username, String password, String name, String surname) {
        super(username, password);
        this.name = name;
        this.surname = surname;
    }

    /**
     * Η μέθοδος αυτή επιστρέφει το όνομα του πελάτη.
     *
     * @return το όνομα του πελάτη.
     */
    public String getName() {
        return name;
    }

    /**
     * Η μέθοδος αυτή επιστρέφει το επώνυμο του πελάτη.
     *
     * @return το επώνυμο του πελάτη.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Η μέθοδος αυτή επιστρέφει το όνομα χρήστη του πελάτη.
     *
     * @return το όνομα χρήστη του πελάτη.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Η μέθοδος αυτή επιστρέφει τον κωδικό πρόσβασης του πελάτη.
     *
     * @return τον κωδικό πρόσβασης του πελάτη.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Η μέθοδος αυτή δέχεται ως όρισμα ένα νέο όνομα και το θέτει για τον πελάτη.
     *
     * @param name το νέο όνομα του πελάτη.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Η μέθοδος αυτή δέχεται ως όρισμα ένα νέο επώνυμο και το θέτει για τον πελάτη.
     *
     * @param surname το νέο επώνυμο του πελάτη.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }
}
