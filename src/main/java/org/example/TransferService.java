package org.example;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TransferService {
    public void transferMoney() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter source account number: ");
        String sourceAccount = scanner.nextLine();
        System.out.print("Enter destination account number: ");
        String destinationAccount = scanner.nextLine();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        boolean isCompleted = false;

        try (Connection conn = DataBaseManager.connect()) {
            conn.setAutoCommit(false);

            // Check if source account has enough balance
            String checkBalanceSql = "SELECT balance FROM accounts WHERE account_number = ?";
            PreparedStatement checkBalanceStmt = conn.prepareStatement(checkBalanceSql);
            checkBalanceStmt.setString(1, sourceAccount);
            ResultSet rs = checkBalanceStmt.executeQuery();
            if (rs.next() && rs.getDouble("balance") >= amount) {
                double newSourceBalance = rs.getDouble("balance") - amount;

                // Update source account
                String updateSourceSql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
                PreparedStatement updateSourceStmt = conn.prepareStatement(updateSourceSql);
                updateSourceStmt.setDouble(1, newSourceBalance);
                updateSourceStmt.setString(2, sourceAccount);
                updateSourceStmt.executeUpdate();

                // Update destination account
                String updateDestSql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
                PreparedStatement updateDestStmt = conn.prepareStatement(updateDestSql);
                updateDestStmt.setDouble(1, amount);
                updateDestStmt.setString(2, destinationAccount);
                updateDestStmt.executeUpdate();

                conn.commit();
                isCompleted = true;
                System.out.println("Transfer successful");
            } else {
                System.out.println("Insufficient funds in the source account.");
            }
            ReportGenerator report = new ReportGenerator(getCurrentDateTime(), sourceAccount, destinationAccount, amount, isCompleted);
            report.generateReport();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}
