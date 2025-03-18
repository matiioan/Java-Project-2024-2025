package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import api.*;

public class MainMenu extends JFrame {
    private JButton actionButton;
    private JButton logoutButton;
    private JButton refreshButton;
    private JButton searchButton;
    private JButton roleBasedButton;
    private JButton createAccountButton;
    public File productsFile = new File("src/files/products.txt");
    private JSplitPane splitPane;
    public Users userService;
    public Basket basket = new Basket();

    public MainMenu(Users userService) {
        this.userService = userService;
        setupUI(userService);
    }
    private void setupUI(Users userService) {

        setTitle("Κύριο Μενού");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        add(mainPanel);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(70, 130, 180));

        JLabel titleLabel = new JLabel("Καλώς ήρθατε, " + userService.getUsername());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(0.2);
        splitPane.setDividerSize(2);
        splitPane.setBackground(new Color(240, 248, 255));

        JPanel leftSidePanel = new JPanel();
        leftSidePanel.setLayout(new BoxLayout(leftSidePanel, BoxLayout.Y_AXIS));
        leftSidePanel.setBackground(new Color(70, 130, 180));
        leftSidePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));

        refreshButton = new JButton("Ανανέωση Προϊόντων");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 16));
        refreshButton.setBackground(Color.WHITE);
        refreshButton.setForeground(new Color(70, 130, 180));
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(new RefreshButtonListener());

        searchButton = new JButton("Αναζήτηση Προϊόντων");
        searchButton.setFont(new Font("Arial", Font.BOLD, 16));
        searchButton.setBackground(Color.WHITE);
        searchButton.setForeground(new Color(70, 130, 180));
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProjectManagerGUI searchWindow = new ProjectManagerGUI(userService,basket);
                searchWindow.setVisible(true);
            }
        });

        actionButton = new JButton();
        actionButton.setFont(new Font("Arial", Font.BOLD, 16));
        actionButton.setBackground(Color.WHITE);
        actionButton.setForeground(new Color(70, 130, 180));
        actionButton.setFocusPainted(false);
        actionButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        roleBasedButton = new JButton();
        roleBasedButton.setFont(new Font("Arial", Font.BOLD, 16));
        roleBasedButton.setBackground(Color.WHITE);
        roleBasedButton.setForeground(new Color(70, 130, 180));
        roleBasedButton.setFocusPainted(false);
        roleBasedButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));


        if (userService.getRoleID() == 1) {
            Admins admin = new Admins(userService.getUsername(), userService.getPassword());
            actionButton.setText("Προσθήκη Προϊόντος");
            actionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new AddProduct(admin);
                }
            });
        } else if (userService.getRoleID() == 2) {
            actionButton.setText("Καλάθι");
            actionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new BasketGUI(basket, userService);
                }
            });
        }
        if (userService.getRoleID() == 1) {
            roleBasedButton.setText("Στατιστικά");
            roleBasedButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Statistics();
                }
            });
        } else if (userService.getRoleID() == 2) {
            roleBasedButton.setText("Ιστορικό Παραγγελιών");
            roleBasedButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new OrderHistory(userService);
                }
            });
        }

        leftSidePanel.add(Box.createVerticalGlue());
        leftSidePanel.add(refreshButton);
        leftSidePanel.add(Box.createVerticalStrut(20));
        leftSidePanel.add(searchButton);
        leftSidePanel.add(Box.createVerticalStrut(20));
        leftSidePanel.add(actionButton);
        leftSidePanel.add(Box.createVerticalStrut(20));
        leftSidePanel.add(roleBasedButton);
        leftSidePanel.add(Box.createVerticalGlue());

        splitPane.setLeftComponent(leftSidePanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(new Color(240, 248, 255));

        loadProducts(rightPanel);

        JScrollPane scrollPane = new JScrollPane(rightPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        splitPane.setRightComponent(scrollPane);

        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(new Color(240, 248, 255));
        logoutButton = new JButton("Αποσύνδεση");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setBackground(new Color(20, 117, 220));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        logoutButton.addActionListener(new LogoutButtonListener());

        footerPanel.add(logoutButton);
        mainPanel.add(splitPane, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadProducts(JPanel rightPanel) {
        try (BufferedReader br = new BufferedReader(new FileReader(productsFile))) {
            String line;
            String title = null;
            String description = null;

            rightPanel.removeAll();

            while ((line = br.readLine()) != null) {
                if (line.startsWith("Τίτλος:")) {
                    title = line.substring(7).trim();
                } else if (line.startsWith("Περιγραφή: ")) {
                    description = line.substring(11);
                }

                if (title != null && description != null) {
                    addProductToPanel(rightPanel, title, description);
                    title = null;
                    description = null;
                }
            }

            rightPanel.revalidate();
            rightPanel.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addProductToPanel(JPanel panel, String title, String description) {
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        productPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        productPanel.setBackground(Color.WHITE);

        JButton titleButton = new JButton(title);
        titleButton.setFont(new Font("Arial", Font.BOLD, 18));
        titleButton.setForeground(new Color(70, 130, 180));
        titleButton.setBorderPainted(false);
        titleButton.setContentAreaFilled(false);
        titleButton.setFocusPainted(false);

        titleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openProductPage(title);
            }
        });

        JLabel descriptionLabel = new JLabel("<html>" + description + "</html>");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        productPanel.add(titleButton);
        productPanel.add(Box.createVerticalStrut(5));
        productPanel.add(descriptionLabel);

        panel.add(productPanel);
        panel.add(Box.createVerticalStrut(10));
    }

    private void openProductPage(String productTitle) {
        new ProductPage(productTitle, userService, basket);
    }

    private class RefreshButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel rightPanel = (JPanel) ((JScrollPane) splitPane.getRightComponent()).getViewport().getView();
            loadProducts(rightPanel);
        }
    }

    private class LogoutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(MainMenu.this, "Είστε σίγουροι ότι θέλετε να αποσυνδεθείτε;", "Επιβεβαίωση Αποσύνδεσης", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new Login();
            }
        }
    }
}
