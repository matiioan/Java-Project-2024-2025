package gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Statistics extends JFrame {
    private JTextArea outOfStockArea;
    public File productsFile = new File("src/files/products.txt");

    public Statistics() {
        setupUI();
        displayOutOfStockProducts();
    }

    private void setupUI() {
        setTitle("Στατιστικά Προϊόντων");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Εξαντλημένα Προϊόντα", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));


        outOfStockArea = new JTextArea();
        outOfStockArea.setEditable(false); // Μη επεξεργάσιμο
        JScrollPane scrollPane = new JScrollPane(outOfStockArea);

        panel.add(headerLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }

    private void displayOutOfStockProducts() {
        List<String> outOfStockProducts = parseProductsFromFile(productsFile);

        if (outOfStockProducts.isEmpty()) {
            outOfStockArea.setText("Δεν υπάρχουν προϊόντα που να είναι εξαντλημένα.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String product : outOfStockProducts) {
                sb.append(product).append("\n");
            }
            outOfStockArea.setText(sb.toString());
        }
    }

    private List<String> parseProductsFromFile(File file) {
        List<String> outOfStockProducts = new ArrayList<>();
        String title = null;
        double quantity = -1;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("Τίτλος:")) {

                    title = line.replace("Τίτλος:", "").trim();
                } else if (line.startsWith("Ποσότητα:")) {

                    String quantityText = line.replace("Ποσότητα:", "").trim();
                    try {

                        quantity = Double.parseDouble(quantityText.split(" ")[0]);
                    } catch (NumberFormatException e) {

                        quantity = -1;
                    }
                }


                if (title != null && quantity != -1) {
                    if (quantity == 0.0) {
                        outOfStockProducts.add(title);
                    }
                    title = null;
                    quantity = -1;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάγνωση του αρχείου προϊόντων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }

        return outOfStockProducts;
    }
}
