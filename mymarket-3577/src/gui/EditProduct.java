package gui;

import javax.swing.*;
import java.awt.*;
import api.*;

public class EditProduct extends JFrame {

    public EditProduct(Product product) {
        String oldTitle = product.getTitle();
        setTitle("Επεξεργασία Προϊόντος - " + product.getTitle());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        add(mainPanel);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel("Επεξεργασία: " + product.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(70, 130, 180));
        titlePanel.add(titleLabel);

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel nameLabel = new JLabel("Όνομα:");
        JTextField nameField = new JTextField(product.getTitle());

        JLabel categoryLabel = new JLabel("Κατηγορία:");
        JTextField categoryField = new JTextField(product.getCategory());

        JLabel subCategoryLabel = new JLabel("Υποκατηγορία:");
        JTextField subCategoryField = new JTextField(product.getSubCategory());

        JLabel priceLabel = new JLabel("Τιμή:");
        JTextField priceField = new JTextField(String.valueOf(product.getPrice()));

        JLabel quantityLabel = new JLabel("Ποσότητα:");
        JTextField quantityField = new JTextField(String.valueOf(product.getQuantity()));

        JLabel descriptionLabel = new JLabel("Περιγραφή:");
        JTextArea descriptionArea = new JTextArea(product.getDescription());
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(categoryLabel);
        formPanel.add(categoryField);
        formPanel.add(subCategoryLabel);
        formPanel.add(subCategoryField);
        formPanel.add(priceLabel);
        formPanel.add(priceField);
        formPanel.add(quantityLabel);
        formPanel.add(quantityField);
        formPanel.add(descriptionLabel);
        formPanel.add(descriptionScrollPane);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(240, 248, 255));

        JButton saveButton = new JButton("Αποθήκευση");
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.setBackground(new Color(70, 130, 180));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        saveButton.addActionListener(e -> {
            try {
                product.setTitle(nameField.getText());
                product.setCategory(categoryField.getText());
                product.setSubcategory(subCategoryField.getText());
                product.setPrice(Double.parseDouble(priceField.getText()));
                product.setQuantity(Double.parseDouble(quantityField.getText()));
                product.setDescription(descriptionArea.getText());

                ProductManager productManager = new ProductManager();
                productManager.saveProduct(product, oldTitle);

                JOptionPane.showMessageDialog(this, "Το προϊόν ενημερώθηκε με επιτυχία!");
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Παρακαλώ εισάγετε έγκυρες τιμές.");
            }
        });

        buttonPanel.add(saveButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
