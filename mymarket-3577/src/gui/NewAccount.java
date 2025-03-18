package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import api.Users;

public class NewAccount extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JTextField surnameField;
    private JButton createButton;
    Users userService = new Users();

    public NewAccount() {
        setupUI();
    }

    private void setupUI() {
        setTitle("Create New Account");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        add(mainPanel);

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Fields
        addField(centerPanel, gbc, "Username:", usernameField = new JTextField(20));
        gbc.gridy++;
        addField(centerPanel, gbc, "Password:", passwordField = new JPasswordField(20));
        gbc.gridy++;
        addField(centerPanel, gbc, "First Name:", nameField = new JTextField(20));
        gbc.gridy++;
        addField(centerPanel, gbc, "Surname:", surnameField = new JTextField(20));

        // Button
        gbc.gridy++;
        gbc.gridwidth = 2;
        createButton = new JButton("Create Account");
        createButton.setFont(new Font("Arial", Font.BOLD, 18));
        createButton.setBackground(new Color(70, 130, 180));
        createButton.setForeground(Color.WHITE);
        createButton.addActionListener(new CreateAccountButtonListener());
        centerPanel.add(createButton, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
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

    private class CreateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String name = nameField.getText().trim();
            String surname = surnameField.getText().trim();

           if (username.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty()) {
                JOptionPane.showMessageDialog(NewAccount.this, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }



            boolean isUser =userService.newUser(username, password, name, surname);
            if (isUser){
                JOptionPane.showMessageDialog(NewAccount.this, "Ο Λογαριασμός δημιουργήθηκε!");
            }else{
                JOptionPane.showMessageDialog(NewAccount.this, "Το username υπάρχει ήδη", "Error", JOptionPane.ERROR_MESSAGE);
            }

            dispose();
            new Login();
        }
    }
}
