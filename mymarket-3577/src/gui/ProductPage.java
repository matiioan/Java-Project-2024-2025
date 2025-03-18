package gui;

import javax.swing.*;
import java.awt.*;
import api.*;

public class ProductPage extends JFrame {
    private String productTitle;
    private double quant = 0;
    public Basket basket;

    public ProductPage(String productTitle, Users user, Basket basket) {
        this.productTitle = productTitle;
        ProductManager productManager = new ProductManager();
        Product product = productManager.loadProduct(productTitle);
        this.basket = basket;

        setupUI(product, user);
    }

    private void setupUI(Product product, Users user) {
        setTitle("Σελίδα Προϊόντος - " + productTitle);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        add(mainPanel);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel(product.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(70, 130, 180));

        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel descriptionLabel = new JLabel("<html><b>Περιγραφή:</b> " + product.getDescription() + "</html>");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionLabel.setForeground(new Color(70, 130, 180));

        JLabel categoryLabel = new JLabel("<html><b>Κατηγορία:</b> " + product.getCategory() + "</html>");
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        categoryLabel.setForeground(new Color(70, 130, 180));

        JLabel subcategoryLabel = new JLabel("<html><b>Υποκατηγορία:</b> " + product.getSubCategory() + "</html>");
        subcategoryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subcategoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        subcategoryLabel.setForeground(new Color(70, 130, 180));

        JLabel priceLabel = new JLabel("<html><b>Τιμή:</b> $" + product.getPrice() + "</html>");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        priceLabel.setForeground(new Color(70, 130, 180));

        JLabel quantityLabel = new JLabel("<html><b>Ποσότητα:</b> " + product.getQuantity() + "</html>");
        quantityLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        quantityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        quantityLabel.setForeground(new Color(70, 130, 180));

        detailsPanel.add(descriptionLabel);
        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(categoryLabel);
        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(subcategoryLabel);
        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(priceLabel);
        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(quantityLabel);

        if (user.getRoleID() == 2) {
            JPanel quantityPanel = new JPanel();
            quantityPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel inputQuantityLabel = new JLabel("Εισάγετε Ποσότητα: ");
            inputQuantityLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            JTextField quantityTextField = new JTextField(5);
            quantityPanel.add(inputQuantityLabel);
            quantityPanel.add(quantityTextField);

            detailsPanel.add(quantityPanel);

            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            bottomPanel.setBackground(new Color(240, 248, 255));

            JButton addToBasketButton = new JButton("Προσθήκη στο Καλάθι");
            addToBasketButton.setFont(new Font("Arial", Font.BOLD, 16));
            addToBasketButton.setBackground(new Color(70, 130, 180));
            addToBasketButton.setForeground(Color.WHITE);
            addToBasketButton.setFocusPainted(false);
            addToBasketButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            addToBasketButton.addActionListener(e -> {
                try {
                    quant = Double.parseDouble(quantityTextField.getText());

                    if((quant <= product.getQuantity()) && quant >= 0){
                        basket.AddProductToBasket(product, quant);
                        JOptionPane.showMessageDialog(this, "Το προϊόν προστέθηκε στο καλάθι!");
                    } else if (quant < 0) {
                        JOptionPane.showMessageDialog(this, "Μη έγκυρη ποσότητα");
                    } else {
                        JOptionPane.showMessageDialog(this, "Δεν υπάρχει αρκετό από το προϊόν!");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Παρακαλώ εισάγετε έγκυρη ποσότητα.");
                }
            });

            bottomPanel.add(addToBasketButton);
            mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        } else if (user.getRoleID() == 1) {
            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            bottomPanel.setBackground(new Color(240, 248, 255));

            JButton editProductButton = new JButton("Επεξεργασία Προϊόντος");
            editProductButton.setFont(new Font("Arial", Font.BOLD, 16));
            editProductButton.setBackground(new Color(70, 130, 180));
            editProductButton.setForeground(Color.WHITE);
            editProductButton.setFocusPainted(false);
            editProductButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            editProductButton.addActionListener(e -> {
                new EditProduct(product);
            });

            bottomPanel.add(editProductButton);
            mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        }

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
