package gui;

import javax.swing.*;
import java.awt.*;
import api.*;

public class AddProduct extends JFrame {
    private JTextField titleField;
    private JTextField descriptionField;
    private JTextField priceField;
    private JTextField quantityField;
    private JTextField categoryField;
    private JTextField subcategoryField;

    public AddProduct(Admins admin) {
        setupUI(admin);
    }

    private void setupUI(Admins admin) {
        setTitle("Προσθήκη Προιόντος");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));
        panel.setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel("Τίτλος Προιόντος:");
        titleField = new JTextField(15);

        JLabel descriptionLabel = new JLabel("Περιγραφή:");
        descriptionField = new JTextField(15);

        JLabel priceLabel = new JLabel("Τιμή:");
        priceField = new JTextField(15);

        JLabel quantityLabel = new JLabel("Ποσότητα:");
        quantityField = new JTextField(15);

        JLabel categoryLabel = new JLabel("Κατηγορία:");
        categoryField = new JTextField(15);

        JLabel subcategoryLabel = new JLabel("Υποκατηγορία:");
        subcategoryField = new JTextField(15);

        JButton addButton = new JButton("Προσθήκη Προιόντος");
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);

        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String description = descriptionField.getText();
            double price = 0;
            double quantity = 0;

            try {
                price = Double.parseDouble(priceField.getText());
                quantity = Double.parseDouble(quantityField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Η τιμή και η ποσότητα πρέπει να είναι έγκυροι αριθμοί.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String category = categoryField.getText();
            String subcategory = subcategoryField.getText();

            if (title.isEmpty() || description.isEmpty() || category.isEmpty() || subcategory.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Παρακαλώ συμπληρώστε όλα τα πεδία", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            } else if (price <= 0) {
                JOptionPane.showMessageDialog(this, "Η τιμή πρέπει να είναι μεγαλύτερη από το 0", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            } else if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Η ποσότητα πρέπει να είναι μεγαλύτερη από το 0", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            } else {
                admin.productPlacement(title, description, category, subcategory, price, quantity);
                JOptionPane.showMessageDialog(this, "Το Προϊόν Προστέθηκε Επιτυχώς!");
                dispose();
            }
        });

        panel.add(titleLabel);
        panel.add(titleField);
        panel.add(descriptionLabel);
        panel.add(descriptionField);
        panel.add(priceLabel);
        panel.add(priceField);
        panel.add(quantityLabel);
        panel.add(quantityField);
        panel.add(categoryLabel);
        panel.add(categoryField);
        panel.add(subcategoryLabel);
        panel.add(subcategoryField);
        panel.add(new JLabel());
        panel.add(addButton);

        add(panel);

        setVisible(true);
    }
}
