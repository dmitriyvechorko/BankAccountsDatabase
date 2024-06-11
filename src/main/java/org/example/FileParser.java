package org.example;

import java.io.*;
import java.sql.*;

public class FileParser {
    public void processFiles() {
        File inputDirectory = new File("input");
        File[] files = inputDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("No .txt files found in directory");
            return;
        }
        for (File file : files) {
            parseFileAndInsertIntoDatabase(file);
        }

    }

    public void parseFileAndInsertIntoDatabase(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\s++");
                if (data.length != 2) {
                    System.out.println("Invalid string format: " + line);
                    continue;
                }
                String accountNumber = data[0].trim();
                if (!accountNumber.matches("\\d{5}-\\d{5}")) {
                    System.out.println("Invalid account number format: " + accountNumber);
                    continue;
                }

                double balance;
                try {
                    balance = Double.parseDouble(data[1].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid balance format: " + data[1]);
                    continue;
                }

                // Data processing and database updating
                DataBaseManager.updateDatabase(accountNumber, balance);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}

