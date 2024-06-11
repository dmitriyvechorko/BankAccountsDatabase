package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ReportGenerator {

    private String dateTime;
    private String sourceAccount;
    private String destinationAccount;
    private double amount;
    private boolean status;

    public ReportGenerator(String dateTime, String sourceAccount, String destinationAccount, double amount, boolean status) {
        this.dateTime = dateTime;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
        this.status = status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public Double getAmount() {
        return amount;
    }

    public String getStatus() {
        if (status) {
            return "Transaction has been completed successfully";
        } else {
            return "Transaction has been failed";
        }
    }

    public void generateReport() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output/report.txt", true))) {
            String line = STR."\{getDateTime()} | \{getSourceAccount()} | \{getDestinationAccount()} | \{getAmount()} | \{getStatus()}\n";
            writer.write(line);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



