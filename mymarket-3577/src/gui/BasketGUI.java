package gui;

import javax.swing.*;
import java.awt.*;
import api.*;
import java.util.ArrayList;

public class BasketGUI extends JFrame {

    public BasketGUI(Basket basket, Users user) {
        setTitle("Καλάθι Αγορών");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        Color backgroundColor = new Color(230, 240, 255);
        Color buttonColor = new Color(70, 130, 180);
        Color darkBlue = new Color(25, 50, 100);
        Color borderColor = new Color(100, 150, 200);

        Font labelFont = new Font("Arial", Font.BOLD, 18);
        Font listFont = new Font("Arial", Font.PLAIN, 16);

        DefaultListModel<String> titleListModel = new DefaultListModel<>();
        DefaultListModel<String> quantityListModel = new DefaultListModel<>();
        ArrayList<JButton> removeButtons = new ArrayList<>();

        for (int i = 0; i < basket.prTitle.size(); i++) {
            titleListModel.addElement(basket.prTitle.get(i));
            quantityListModel.addElement(String.valueOf(basket.prQuantity.get(i)));

            final int index = i;
            JButton removeButton = new JButton("X");
            removeButton.setBackground(buttonColor);
            removeButton.setForeground(Color.WHITE);
            removeButton.setFont(listFont);
            removeButton.setPreferredSize(new Dimension(80, 30));

            removeButton.addActionListener(e -> {
                basket.RemoveProductFromBasket(basket, index);

                titleListModel.remove(index);
                quantityListModel.remove(index);
                removeButtons.get(index).setEnabled(false);

                refreshBasketGUI(basket, user);
            });

            removeButtons.add(removeButton);
        }

        JPanel productsPanel = new JPanel();
        productsPanel.setLayout(new GridLayout(basket.prTitle.size(), 1));
        productsPanel.setBackground(backgroundColor);

        for (int i = 0; i < basket.prTitle.size(); i++) {
            JPanel productPanel = new JPanel();
            productPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            productPanel.setBackground(backgroundColor);

            JLabel productLabel = new JLabel(basket.prTitle.get(i) + " - Ποσότητα: " + basket.prQuantity.get(i));
            productLabel.setFont(listFont);
            productLabel.setForeground(darkBlue);
            productPanel.add(productLabel);

            productPanel.add(removeButtons.get(i));

            productsPanel.add(productPanel);
        }

        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        totalPanel.setBackground(backgroundColor);

        double total = basket.TotalPrice();

        JLabel totalLabel = new JLabel("Συνολική Τιμή: €" + String.format("%.2f", total), JLabel.LEFT);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setForeground(darkBlue);

        totalPanel.add(totalLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton placeOrderButton = new JButton("Ολοκλήρωση Παραγγελίας");
        placeOrderButton.setBackground(buttonColor);
        placeOrderButton.setForeground(Color.WHITE);
        placeOrderButton.setFont(listFont);
        placeOrderButton.setPreferredSize(new Dimension(120, 30));

        placeOrderButton.addActionListener(e -> {
            basket.OrderToHistory(user, total);
            dispose();
        });

        buttonPanel.add(placeOrderButton);

        add(productsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(totalPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    private void refreshBasketGUI(Basket basket, Users user) {
        getContentPane().removeAll();

        setLayout(new BorderLayout());
        Color backgroundColor = new Color(230, 240, 255);
        Font listFont = new Font("Arial", Font.PLAIN, 16);
        Color darkBlue = new Color(25, 50, 100);

        DefaultListModel<String> titleListModel = new DefaultListModel<>();
        DefaultListModel<String> quantityListModel = new DefaultListModel<>();
        ArrayList<JButton> removeButtons = new ArrayList<>();

        for (int i = 0; i < basket.prTitle.size(); i++) {
            titleListModel.addElement(basket.prTitle.get(i));
            quantityListModel.addElement(String.valueOf(basket.prQuantity.get(i)));

            final int index = i;
            JButton removeButton = new JButton("X");
            removeButton.setBackground(new Color(70, 130, 180));
            removeButton.setForeground(Color.WHITE);
            removeButton.setFont(listFont);
            removeButton.setPreferredSize(new Dimension(80, 30));

            removeButton.addActionListener(e -> {
                basket.RemoveProductFromBasket(basket, index);
                titleListModel.remove(index);
                quantityListModel.remove(index);
                removeButtons.get(index).setEnabled(false);
                refreshBasketGUI(basket, user);
            });

            removeButtons.add(removeButton);
        }

        JPanel productsPanel = new JPanel();
        productsPanel.setLayout(new GridLayout(basket.prTitle.size(), 1));
        productsPanel.setBackground(backgroundColor);

        for (int i = 0; i < basket.prTitle.size(); i++) {
            JPanel productPanel = new JPanel();
            productPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            productPanel.setBackground(backgroundColor);

            JLabel productLabel = new JLabel(basket.prTitle.get(i) + " - Ποσότητα: " + basket.prQuantity.get(i));
            productLabel.setFont(listFont);
            productLabel.setForeground(darkBlue);
            productPanel.add(productLabel);

            productPanel.add(removeButtons.get(i));

            productsPanel.add(productPanel);
        }

        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        totalPanel.setBackground(backgroundColor);

        double total = basket.TotalPrice();
        JLabel totalLabel = new JLabel("Συνολική Τιμή: €" + String.format("%.2f", total), JLabel.LEFT);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setForeground(darkBlue);
        totalPanel.add(totalLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton placeOrderButton = new JButton("Ολοκλήρωση Παραγγελίας");
        placeOrderButton.setBackground(new Color(70, 130, 180));
        placeOrderButton.setForeground(Color.WHITE);
        placeOrderButton.setFont(listFont);
        placeOrderButton.setPreferredSize(new Dimension(120, 30));
        placeOrderButton.addActionListener(e -> {
            basket.OrderToHistory(user, total);
        });

        buttonPanel.add(placeOrderButton);

        add(productsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(totalPanel, BorderLayout.NORTH);

        revalidate();
        repaint();
    }
}
