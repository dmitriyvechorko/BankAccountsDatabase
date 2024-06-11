# **BankAccountsDatabase***

This program provides the ability to work with bank accounts, saving them in a database, and displays transaction logs 
to a report file.

## Class DatabaseManager

This class represents working with a database that stores accounts and their balance.

### *connect()*

The ***''connect()''*** establishes a connection to the database and returns a Connection object,
which can be used to interact with this database.

### *isColumnExists(Connection connection, String tableName, String columnName)*

The ***''isColumnExists()''*** checks whether the specified column exists in the database table. 

### *isValueExists(Connection connection, String tableName, String columnName,Object value)*

The ***''isValueExists()''*** method checks whether a given value exists in a specific column
database tables.

### *createNewDatabase()*

The ***''createNewDatabase''*** method creates a new database and accounts table if they are not already
exist.

### *isColumnExists(Connection connection, String tableName, String columnName)*

The ***''updateDatabase()''*** method updates or inserts a record into the database based on
availability of an account with the specified account number.

## Class FileParser

The **'FileParser'** class is an object that is responsible for parsing files with invoices
and their subsequent addition to the database.

### *processFiles()*

The ***''processFiles()''*** method loads and processes all text files from the input directory,
and then calls the parseFileAndInsertIntoDatabase method on each file.

### *parseFileAndInsertIntoDatabase(File file)*

The ***''parseFileAndInsertIntoDatabase()''*** method parses lines from a text file,
checks their format and updates the database if the string format is correct.

## Class ReportGenerator

The **'ReportGenerator'** class represents an object that is responsible for generating reports
on transfers from one account to another.

### *ReportGenerator (String dateTime, String sourceAccount, String destinationAccount, double amount, boolean status)*
The ***''ReportGenerator()''*** constructor accepts several parameters intended
to initialize the object and report the translation:

* ***dateTime:*** A string representing the date and time of the transaction.

* ***sourceAccount:*** A string representing the number of the source account from which
  translation is made.

* ***destinationAccount:*** A string representing the target account number to which
  translation is made.

* ***amount:*** A floating point number (double) representing the transfer amount.

* ***status:*** A boolean indicating success
  operation. *true* means the operation was successful, and *false* means it failed.

### *generateReport()*

The ***''generateReport()''*** method creates a translation report and writes it to a file
*'output/report.txt'* (if the file doesn't exist, it creates it). 

## Class TransferService

This class is responsible for performing the operation
transferring money from one account to another.

### *transferMoney()*

The ***''transferMoney()''*** method performs the operation of transferring funds from one account to
another. Here's how this method works:

1. *Database Connection*: The method establishes a connection to the database using
   *connect()* method from the *DatabaseManager class*. The connection to the database is made in
   try-with-resources block to ensure the connection is closed after use.

2. *Data entry*: The user is asked to enter the source account number, target account number
   account and transfer amount using the *Scanner* object. The entered data is saved in
   relevant variables.

3. *Check for account existence*: Method calls *isValueExists()* method to check if account exists
   there is an account with the specified number. If the account does not exist, 
   an error message is displayed and
   The method calls itself recursively to re-enter data.

4. *Start Transaction*: Sets the automatic transaction mode manually to
   prevent automatic transaction confirmation after each request is completed.

5. *Balance check and account update*: The method checks the balance of the original account to
   make sure there are enough funds in the account for the transfer. If the balance is sufficient,
   the balance of the source and target accounts is updated by executing
   corresponding *SQL*-queries.

6. *Report Creation*: A *ReportGenerator* object is created, which is used to generate
   translation report. This report includes information about the date and time of the transaction,
   account numbers, transfer amount and transaction status.

## ![*classes UML-diagram*](https://github.com/dmitriyvechorko/Test/blob/main/diag1.png)