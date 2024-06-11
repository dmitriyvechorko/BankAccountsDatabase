package org.example.databaseInteractions;

import java.sql.*;

public class DatabaseManager {
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

    public static boolean isColumnExists(Connection connection, String tableName, String columnName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet columns = metaData.getColumns(null, null, tableName, columnName)) {
            return columns.next();
        }
    }

    public static boolean isValueExists(Connection connection, String tableName, String columnName, Object value) throws SQLException {
        if (!isColumnExists(connection, tableName, columnName)) {
            System.out.println("Column " + columnName + " does not exist in table " + tableName);
            return false;
        }

        String query = String.format("SELECT 1 FROM %s WHERE %s = ?", tableName, columnName);
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setObject(1, value);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
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
            // Checking if the account exists in the database
            String checkAccountQuery = "SELECT balance FROM accounts WHERE account_number = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkAccountQuery)) {
                checkStmt.setString(1, accountNumber);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    // The account exists, update the balance
                    String updateQuery = "UPDATE accounts SET balance = ? WHERE account_number = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setDouble(1, balance);
                        updateStmt.setString(2, accountNumber);
                        updateStmt.executeUpdate();
                        System.out.println("Account updated: " + accountNumber + " with balance: " + balance);
                    }
                } else {
                    // Account does not exist, add a new account
                    String insertQuery = "INSERT INTO accounts (account_number, balance) VALUES (?, ?)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                        insertStmt.setString(1, accountNumber);
                        insertStmt.setDouble(2, balance);
                        insertStmt.executeUpdate();
                        System.out.println("New account added: " + accountNumber + " with balance: " + balance);
                    }
                }
            }
        }
    }
}
