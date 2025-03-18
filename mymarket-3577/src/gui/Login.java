package gui;

import api.Users;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;
    private JLabel messageLabel;

    public Login() {
        setupUI();
    }

    private void setupUI() {
        setTitle("Σύνδεση Χρήστη");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        add(mainPanel);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Σύνδεση Χρήστη");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        addField(centerPanel, gbc, "Όνομα χρήστη:", usernameField = new JTextField(20));
        gbc.gridy++;
        addField(centerPanel, gbc, "Κωδικός πρόσβασης:", passwordField = new JPasswordField(20));

        gbc.gridy++;
        gbc.gridwidth = 2;
        loginButton = new JButton("Σύνδεση");
        styleButton(loginButton);
        centerPanel.add(loginButton, gbc);

        gbc.gridy++;
        createAccountButton = new JButton("Δημιουργία Λογαριασμού");
        styleButton(createAccountButton);
        centerPanel.add(createAccountButton, gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        messageLabel = new JLabel();
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setForeground(Color.RED);
        centerPanel.add(messageLabel, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewAccount();
            }
        });

        setVisible(true);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(label, gbc);
        gbc.gridx = 1;
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
        panel.add(field, gbc);
        gbc.gridx = 0;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        Users user = new Users(username, password);
        if (user.findUser(username, password)) {
            int roleID = user.getRoleID();
            if (roleID == 1) {
                JOptionPane.showMessageDialog(this, "Καλώς ήρθατε, διαχειριστή!");
                new MainMenu(user);
            } else if (roleID == 2) {
                JOptionPane.showMessageDialog(this, "Καλώς ήρθατε, πελάτη!");
                new MainMenu(user);
            }
            dispose();
        } else {
            if (user.scenarios == 1) {
                messageLabel.setText("Λάθος κωδικός πρόσβασης!");
            } else if (user.scenarios == 2) {
                messageLabel.setText("Ο χρήστης δεν βρέθηκε!");
            } else {
                messageLabel.setText("Αποτυχία σύνδεσης!");
            }
        }
    }
}
