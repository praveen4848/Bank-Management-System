import java.sql.*;

public class BankOperations {

    public static double getBalance(String username) {
        String sql = "SELECT balance FROM users WHERE username=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static void deposit(String username, double amount) {
        String update = "UPDATE users SET balance = balance + ? WHERE username=?";
        String insert = "INSERT INTO transactions (username, type, amount) VALUES (?, 'DEPOSIT', ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt1 = conn.prepareStatement(update);
             PreparedStatement stmt2 = conn.prepareStatement(insert)) {
            conn.setAutoCommit(false);

            stmt1.setDouble(1, amount);
            stmt1.setString(2, username);
            stmt1.executeUpdate();

            stmt2.setString(1, username);
            stmt2.setDouble(2, amount);
            stmt2.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean withdraw(String username, double amount) {
        double balance = getBalance(username);
        if (balance < amount) {
            return false;
        }
        String update = "UPDATE users SET balance = balance - ? WHERE username=?";
        String insert = "INSERT INTO transactions (username, type, amount) VALUES (?, 'WITHDRAW', ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt1 = conn.prepareStatement(update);
             PreparedStatement stmt2 = conn.prepareStatement(insert)) {
            conn.setAutoCommit(false);

            stmt1.setDouble(1, amount);
            stmt1.setString(2, username);
            stmt1.executeUpdate();

            stmt2.setString(1, username);
            stmt2.setDouble(2, amount);
            stmt2.executeUpdate();

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getHistory(String username) {
        StringBuilder history = new StringBuilder();
        String sql = "SELECT type, amount, timestamp FROM transactions WHERE username=? ORDER BY timestamp DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                history.append(rs.getString("timestamp"))
                        .append(" - ")
                        .append(rs.getString("type"))
                        .append(" ₹")
                        .append(rs.getDouble("amount"))
                        .append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history.toString();
    }
}
