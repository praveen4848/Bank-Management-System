import javax.swing.*;

public class Login {
    private static UserDAO userDAO = new UserDAO();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bank Login");
        frame.setSize(350, 220);
        frame.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 30, 100, 30);
        JTextField userField = new JTextField();
        userField.setBounds(130, 30, 150, 30);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 70, 100, 30);
        JPasswordField passField = new JPasswordField();
        passField.setBounds(130, 70, 150, 30);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(50, 120, 100, 30);
        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(170, 120, 100, 30);

        frame.add(userLabel);
        frame.add(userField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(loginBtn);
        frame.add(registerBtn);

        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            if (userDAO.loginUser(username, password)) {
                frame.dispose();
                Dashboard.showDashboard(username);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials!");
            }
        });

        registerBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            if (userDAO.registerUser(username, password)) {
                JOptionPane.showMessageDialog(frame, "User registered! Login now.");
            } else {
                JOptionPane.showMessageDialog(frame, "Registration failed!");
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
