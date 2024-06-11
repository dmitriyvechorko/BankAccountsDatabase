package org.example;

import java.sql.*;

public class DataBaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:data";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
            if (conn != null) {
                System.out.println("Connected to the database");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                stmt.execute("CREATE TABLE IF NOT EXISTS accounts (" +
                        "account_id INTEGER PRIMARY KEY," +
                        "account_number TEXT NOT NULL," +
                        "balance DOUBLE NOT NULL" +
                        ");");
                System.out.println("Database and table created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateDatabase(String accountNumber, double balance) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
            // Проверяем, существует ли счет в базе данных
            String checkAccountQuery = "SELECT balance FROM accounts WHERE account_number = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkAccountQuery)) {
                checkStmt.setString(1, accountNumber);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    // Счет существует, обновляем баланс
                    String updateQuery = "UPDATE accounts SET balance = ? WHERE account_number = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setDouble(1, balance);
                        updateStmt.setString(2, accountNumber);
                        updateStmt.executeUpdate();
                        System.out.println("Обновлен счёт: " + accountNumber + " с балансом: " + balance);
                    }
                } else {
                    // Счет не существует, добавляем новый счет
                    String insertQuery = "INSERT INTO accounts (account_number, balance) VALUES (?, ?)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                        insertStmt.setString(1, accountNumber);
                        insertStmt.setDouble(2, balance);
                        insertStmt.executeUpdate();
                        System.out.println("Добавлен новый счёт: " + accountNumber + " с балансом: " + balance);
                    }
                }
            }
        }
    }
}
