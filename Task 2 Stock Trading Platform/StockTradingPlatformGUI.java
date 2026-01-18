import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

class Stock {
    String symbol;
    double price;

    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
}

class User {
    double balance;
    Map<String, Integer> portfolio = new HashMap<>();

    User(double balance) {
        this.balance = balance;
    }
}

public class StockTradingPlatformGUI extends JFrame {

    private JTable marketTable, portfolioTable;
    private DefaultTableModel marketModel, portfolioModel;
    private JTextField symbolField, quantityField;
    private JLabel balanceLabel, totalValueLabel;

    private Map<String, Stock> market = new HashMap<>();
    private User user = new User(10000);

    public StockTradingPlatformGUI() {

        setTitle("Stock Trading Platform");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        market.put("BankNifty", new Stock("BankNifty", 180));
        market.put("Nifty50", new Stock("Nifty50", 140));
        market.put("Sensex", new Stock("Sensex", 250));

        marketModel = new DefaultTableModel(new String[]{"Stock", "Price"}, 0);
        for (Stock s : market.values()) {
            marketModel.addRow(new Object[]{s.symbol, s.price});
        }
        marketTable = new JTable(marketModel);

        portfolioModel = new DefaultTableModel(new String[]{"Stock", "Quantity", "Value"}, 0);
        portfolioTable = new JTable(portfolioModel);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Trade"));

        inputPanel.add(new JLabel("Stock Symbol:"));
        symbolField = new JTextField();
        inputPanel.add(symbolField);

        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        JButton buyButton = new JButton("Buy");
        JButton sellButton = new JButton("Sell");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(buyButton);
        buttonPanel.add(sellButton);

        balanceLabel = new JLabel("Balance: ₹" + user.balance);
        totalValueLabel = new JLabel("Total Portfolio Value: ₹" + user.balance);

        JPanel summaryPanel = new JPanel(new GridLayout(2, 1));
        summaryPanel.add(balanceLabel);
        summaryPanel.add(totalValueLabel);

        setLayout(new BorderLayout(10, 10));
        add(new JScrollPane(marketTable), BorderLayout.WEST);
        add(new JScrollPane(portfolioTable), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(summaryPanel, BorderLayout.EAST);

        buyButton.addActionListener(e -> buyStock());
        sellButton.addActionListener(e -> sellStock());
    }

    private void buyStock() {
        String symbol = symbolField.getText();
        String qtyText = quantityField.getText();

        if (!market.containsKey(symbol)) {
            JOptionPane.showMessageDialog(this, "Stock not found!");
            return;
        }

        try {
            int qty = Integer.parseInt(qtyText);
            double cost = market.get(symbol).price * qty;

            if (cost > user.balance) {
                JOptionPane.showMessageDialog(this, "Insufficient balance!");
                return;
            }

            user.balance -= cost;
            user.portfolio.put(symbol, user.portfolio.getOrDefault(symbol, 0) + qty);
            updatePortfolio();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid quantity!");
        }
    }

    private void sellStock() {
        String symbol = symbolField.getText();
        String qtyText = quantityField.getText();

        if (!user.portfolio.containsKey(symbol)) {
            JOptionPane.showMessageDialog(this, "Stock not owned!");
            return;
        }

        try {
            int qty = Integer.parseInt(qtyText);
            int owned = user.portfolio.get(symbol);

            if (qty > owned) {
                JOptionPane.showMessageDialog(this, "Not enough stocks!");
                return;
            }

            user.balance += market.get(symbol).price * qty;
            user.portfolio.put(symbol, owned - qty);
            updatePortfolio();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid quantity!");
        }
    }

    private void updatePortfolio() {
        portfolioModel.setRowCount(0);

        double totalValue = user.balance;

        for (String symbol : user.portfolio.keySet()) {
            int qty = user.portfolio.get(symbol);
            double value = qty * market.get(symbol).price;
            totalValue += value;
            portfolioModel.addRow(new Object[]{symbol, qty, value});
        }

        balanceLabel.setText("Balance: ₹" + user.balance);
        totalValueLabel.setText("Total Portfolio Value: ₹" + totalValue);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StockTradingPlatformGUI().setVisible(true);
        });
    }
}
