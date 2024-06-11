package org.example;

import org.example.databaseInteractions.DatabaseManager;
import org.example.databaseInteractions.TransferService;
import org.example.fileProcessing.FileParser;

import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        DatabaseManager.createNewDatabase();

        Scanner scanner = new Scanner(System.in);
        FileParser parser = new FileParser();
        TransferService transferService = new TransferService();

        while (true) {
            System.out.println("1. Parse files");
            System.out.println("2. Transfer money");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    parser.processFiles();
                    break;
                case 2:
                    transferService.transferMoney();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
