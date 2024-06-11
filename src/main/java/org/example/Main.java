package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataBaseManager.createNewDatabase();

        Scanner scanner = new Scanner(System.in);
        FileParser parser = new FileParser();
        TransferService transferService = new TransferService();

        while (true) {
            System.out.println("1. Parse files");
            System.out.println("2. Transfer money");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    parser.parseFileAndInsertIntoDatabase("input/*.txt");
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
