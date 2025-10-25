import javax.swing.*;

public class Dashboard {
    public static void showDashboard(String username) {
        JFrame frame = new JFrame("Welcome: " + username);
        frame.setSize(400, 350);
        frame.setLayout(null);

        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton balanceBtn = new JButton("Check Balance");
        JButton historyBtn = new JButton("View History");
        JButton logoutBtn = new JButton("Logout");

        depositBtn.setBounds(120, 30, 150, 30);
        withdrawBtn.setBounds(120, 70, 150, 30);
        balanceBtn.setBounds(120, 110, 150, 30);
        historyBtn.setBounds(120, 150, 150, 30);
        logoutBtn.setBounds(120, 190, 150, 30);

        frame.add(depositBtn);
        frame.add(withdrawBtn);
        frame.add(balanceBtn);
        frame.add(historyBtn);
        frame.add(logoutBtn);

        depositBtn.addActionListener(e -> {
            String amtStr = JOptionPane.showInputDialog("Enter amount to deposit:");
            if (amtStr != null) {
                double amt = Double.parseDouble(amtStr);
                BankOperations.deposit(username, amt);
                JOptionPane.showMessageDialog(frame, "Deposited ₹" + amt);
            }
        });

        withdrawBtn.addActionListener(e -> {
            String amtStr = JOptionPane.showInputDialog("Enter amount to withdraw:");
            if (amtStr != null) {
                double amt = Double.parseDouble(amtStr);
                if (BankOperations.withdraw(username, amt)) {
                    JOptionPane.showMessageDialog(frame, "Withdrawn ₹" + amt);
                } else {
                    JOptionPane.showMessageDialog(frame, "Insufficient balance!");
                }
            }
        });

        balanceBtn.addActionListener(e -> {
            double bal = BankOperations.getBalance(username);
            JOptionPane.showMessageDialog(frame, "Current Balance: ₹" + bal);
        });

        historyBtn.addActionListener(e -> {
            String history = BankOperations.getHistory(username);
            JTextArea area = new JTextArea(history);
            JScrollPane scrollPane = new JScrollPane(area);
            scrollPane.setPreferredSize(new java.awt.Dimension(300, 200));
            JOptionPane.showMessageDialog(frame, scrollPane, "Transaction History", JOptionPane.INFORMATION_MESSAGE);
        });

        logoutBtn.addActionListener(e -> {
            frame.dispose();
            Login.main(null);
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
