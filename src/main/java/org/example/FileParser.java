package org.example;

import java.io.*;
import java.nio.file.*;
import java.sql.*;

public class FileParser {

    public void parseFileAndInsertIntoDatabase(String filename) {
        Path filePath = Paths.get(filename);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\s++");
                if (data.length != 2) {
                    System.out.println("Неверный формат строки: " + line);
                    continue;
                }
                String accountNumber = data[0].trim();
                if (!accountNumber.matches("\\d{5}-\\d{5}")) {
                    System.out.println("Неверный формат номера счета: " + accountNumber);
                    continue;
                }

                double balance;
                try {
                    balance = Double.parseDouble(data[1].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат баланса: " + data[1]);
                    continue;
                }

                // бработка данных и обновление базы данных
                DataBaseManager.updateDatabase(accountNumber, balance);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}

