package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import api.*;

public class ProjectManagerGUI extends JFrame {
    private ArrayList<Product> products = new ArrayList<>();
    private JTextField searchField;
    private JComboBox<String> categoryComboBox;
    private JButton searchButton;
    private JButton resetButton;

    // Replacing JEditorPane with JPanel for dynamic results
    private JPanel resultPanel;
    private JScrollPane scrollPane;

    // Predefined categories
    private java.util.List<String> categories = Arrays.asList(
            "Όλες", "Φρέσκα τρόφιμα", "Κατεψυγμένα τρόφιμα", "Προϊόντα ψυγείου",
            "Αλλαντικά", "Αλκοολούχα ποτά", "Μη αλκοολούχα ποτά",
            "Καθαριστικά για το σπίτι", "Απορρυπαντικά ρούχων", "Καλλυντικά",
            "Προϊόντα στοματικής υγιεινής", "Πάνες", "Δημητριακά",
            "Ζυμαρικά", "Σνακ", "Έλαια", "Κονσέρβες", "Χαρτικά");

    // Constructor
    public ProjectManagerGUI(Users user, Basket basket) {
        setTitle("Αναζήτηση Προϊόντων");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input fields for search
        searchField = new JTextField();
        categoryComboBox = new JComboBox<>(categories.toArray(new String[0]));
        searchButton = new JButton("Αναζήτηση");
        resetButton = new JButton("Επαναφορά");

        // Initialize result area as a JPanel inside a JScrollPane
        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(resultPanel);

        // Search button functionality
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText().trim();
                String selectedCategory = categoryComboBox.getSelectedItem().toString();

                if (!searchTerm.isEmpty() || !selectedCategory.equals("Όλες")) {
                    searchProducts(searchTerm, selectedCategory, user, basket);
                } else {
                    JOptionPane.showMessageDialog(null, "Παρακαλώ εισάγετε μια λέξη-κλειδί ή επιλέξτε κατηγορία.",
                            "Σφάλμα", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Reset button functionality
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchField.setText("");  // Clear search field
                categoryComboBox.setSelectedIndex(0); // Reset category to "Όλες"
                resultPanel.removeAll();  // Clear results
                resultPanel.add(new JLabel("Αναζητήστε προϊόντα με όρους ή επιλέξτε κατηγορία."));
                resultPanel.revalidate();
                resultPanel.repaint();
            }
        });

        // Layout setup
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        topPanel.add(new JLabel("Πληκτρολογήστε μια λέξη-κλειδί:"));
        topPanel.add(searchField);
        topPanel.add(new JLabel("Επιλέξτε Κατηγορία:"));
        topPanel.add(categoryComboBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(searchButton);
        buttonPanel.add(resetButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load products from file
        loadProductsFromFile("products.txt");
    }

    // Load products from file
    public void loadProductsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("files/" + filename))))) {

            String line;
            Product product = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("Τίτλος:")) {
                    if (product != null) {
                        products.add(product);
                    }
                    product = new Product();
                    product.setTitle(line.substring(7).trim());
                } else if (line.startsWith("Περιγραφή:")) {
                    if (product != null) {
                        product.setDescription(line.substring(11).trim());
                    }
                } else if (line.startsWith("Κατηγορία:")) {
                    if (product != null) {
                        product.setCategory(line.substring(10).trim());
                    }
                } else if (line.startsWith("Υποκατηγορία:")) {
                    if (product != null) {
                        product.setSubcategory(line.substring(14).trim());
                    }
                } else if (line.startsWith("Τιμή:")) {
                    if (product != null) {
                        String originalPrice = line.substring(5).trim();
                        String priceString = originalPrice.replaceAll("€", "").trim();
                        priceString = priceString.replace(",", ".");

                        try {
                            product.setPrice(Double.parseDouble(priceString));
                            product.setOriginalPrice(originalPrice);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            if (product != null) {
                products.add(product);
            }

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    // Search products
    public void searchProducts(String searchTerm, String selectedCategory, Users user, Basket basket) {
        resultPanel.removeAll();  // Clear previous results
        boolean found = false;

        for (Product product : products) {
            boolean matchesCategory = selectedCategory.equals("Όλες") || product.getCategory().equals(selectedCategory);
            boolean matchesSearchTerm = product.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    product.getDescription().toLowerCase().contains(searchTerm.toLowerCase());

            if (matchesCategory && matchesSearchTerm) {
                found = true;

                JPanel productPanel = new JPanel();
                productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
                productPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

                JButton titleButton = new JButton("<html><u>" + product.getTitle() + "</u></html>");
                titleButton.setFont(new Font("Arial", Font.BOLD, 14));
                titleButton.setForeground(Color.BLUE);
                titleButton.setContentAreaFilled(false);
                titleButton.setBorderPainted(false);
                titleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                titleButton.addActionListener(e -> new ProductPage(product.getTitle(), user, basket));

                productPanel.add(titleButton);
                productPanel.add(new JLabel("<html><strong>Περιγραφή:</strong> " + product.getDescription() + "</html>"));
                productPanel.add(new JLabel("<html><strong>Κατηγορία:</strong> " + product.getCategory() + "</html>"));
                productPanel.add(new JLabel("<html><strong>Υποκατηγορία:</strong> " + product.getSubCategory() + "</html>"));
                productPanel.add(new JLabel("<html><strong>Τιμή:</strong> " + product.getOriginalPrice() + "</html>"));

                resultPanel.add(productPanel);
                resultPanel.add(Box.createVerticalStrut(10)); // Add space between products
            }
        }

        if (!found) {
            resultPanel.add(new JLabel("Δεν βρέθηκαν προϊόντα που να ταιριάζουν με την αναζήτηση."));
        }

        resultPanel.revalidate();
        resultPanel.repaint();
    }
}
