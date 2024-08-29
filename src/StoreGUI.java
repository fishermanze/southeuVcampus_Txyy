import javax.swing.*;
import java.awt.*;

public class StoreGUI extends JFrame {
    private JPanel mainPanel;
    private JPanel productsPanel;
    private JPanel cartPanel;
    private JPanel checkoutPanel;

    public StoreGUI() {
        setTitle("Online Store");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        productsPanel = createProductsPanel();
        cartPanel = createCartPanel();
        checkoutPanel = createCheckoutPanel();

        mainPanel.add(productsPanel, BorderLayout.WEST);
        mainPanel.add(cartPanel, BorderLayout.CENTER);
        mainPanel.add(checkoutPanel, BorderLayout.EAST);

        setContentPane(mainPanel);
    }

    private JPanel createProductsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add product items here
        panel.add(createProductItem("Product 1", 9.99));
        panel.add(createProductItem("Product 2", 14.99));
        panel.add(createProductItem("Product 3", 19.99));
        // Add more product items as needed

        return panel;
    }

    private JPanel createProductItem(String name, double price) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel nameLabel = new JLabel(name);
        JLabel priceLabel = new JLabel(String.format("$%.2f", price));
        JButton addToCartButton = new JButton("Add to Cart");

        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(priceLabel, BorderLayout.CENTER);
        panel.add(addToCartButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Cart"));

        // Add cart items here
        JList<String> cartList = new JList<>();
        cartList.setListData(new String[]{"Product 1 - $9.99", "Product 2 - $14.99"});
        JScrollPane cartScrollPane = new JScrollPane(cartList);

        panel.add(cartScrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCheckoutPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Checkout"));

        JLabel totalLabel = new JLabel("Total: $24.98");
        JButton proceedButton = new JButton("Proceed to Checkout");
        JButton clearCartButton = new JButton("Clear Cart");

        panel.add(totalLabel);
        panel.add(proceedButton);
        panel.add(clearCartButton);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StoreGUI storeGUI = new StoreGUI();
            storeGUI.setVisible(true);
        });
    }
}