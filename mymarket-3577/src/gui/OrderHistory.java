package gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import api.Users;

public class OrderHistory extends JFrame {
    private Users user;
    static File ordersFile = new File("src/files/orders.txt");

    public OrderHistory(Users user) {
        this.user = user;
        setTitle("Ιστορικό Παραγγελιών");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 248, 255));

        loadOrderHistory(mainPanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        setVisible(true);
    }

    private void loadOrderHistory(JPanel mainPanel) {
        if (!ordersFile.exists()) {
            JOptionPane.showMessageDialog(this, "Το αρχείο παραγγελιών δεν βρέθηκε!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ordersFile))) {
            String line;
            boolean isUserSection = false;
            StringBuilder userOrders = new StringBuilder();

            while ((line = br.readLine()) != null) {
                System.out.println("Ανάγνωση γραμμής: " + line);

                if (line.matches("User:\\s*" + user.getUsername() + "\\s*$")) {
                    isUserSection = true;
                    userOrders.setLength(0);
                    userOrders.append(line).append("\n");
                } else if (isUserSection) {
                    if (line.startsWith("---------------------------")) {
                        isUserSection = false;
                        addOrderToPanel(mainPanel, userOrders.toString());
                    } else {
                        userOrders.append(line).append("\n");
                    }
                }
            }

            if (isUserSection && userOrders.length() > 0) {
                addOrderToPanel(mainPanel, userOrders.toString());
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάγνωση του αρχείου παραγγελιών: " + e.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addOrderToPanel(JPanel panel, String orderData) {
        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));
        orderPanel.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
        orderPanel.setBackground(Color.WHITE);

        JTextArea orderTextArea = new JTextArea(orderData);
        orderTextArea.setEditable(false);
        orderTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        orderTextArea.setBackground(Color.WHITE);
        orderTextArea.setForeground(new Color(70, 130, 180));

        orderPanel.add(orderTextArea);
        panel.add(orderPanel);
        panel.add(Box.createVerticalStrut(10));
    }
}
