package main;

import model.Client;
import service.BankService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        BankService bankService = new BankService();

        while (true) {

            System.out.println("\n===== Bank =====");
            System.out.println("1. Add client");
            System.out.println("2. Open account");
            System.out.println("3. Op Up Balance");
            System.out.println("4. Withdraw money");
            System.out.println("5. Transfer between accounts");
            System.out.println("6. Check balance");
            System.out.println("7. Transaction history");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Full name: ");
                    String name = scanner.nextLine();

                    System.out.print("Phone number: ");
                    String phone = scanner.nextLine();

                    bankService.addClient(
                            new Client(id, name, phone)
                    );
                    break;

                case 2:

                    System.out.print("client's ID: ");
                    int clientId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Type of accounts (DEBIT/CREDIT): ");
                    String type = scanner.nextLine();

                    bankService.createAccount(clientId, type);
                    break;

                case 3:

                    System.out.print("Account's number: ");
                    String depAcc = scanner.nextLine();

                    System.out.print("Amount: ");
                    double dep = scanner.nextDouble();

                    bankService.deposit(depAcc, dep);
                    break;

                case 4:

                    System.out.print("Account's number: ");
                    String withAcc = scanner.nextLine();

                    System.out.print("Amount: ");
                    double with = scanner.nextDouble();

                    bankService.withdraw(withAcc, with);
                    break;

                case 5:

                    System.out.print("From: ");
                    String from = scanner.nextLine();

                    System.out.print("To: ");
                    String to = scanner.nextLine();

                    System.out.print("Amount: ");
                    double amount = scanner.nextDouble();

                    bankService.transfer(from, to, amount);
                    break;

                case 6:

                    System.out.print("Account's number: ");
                    String acc = scanner.nextLine();

                    System.out.println(
                            "Balance: "
                                    + bankService.getBalance(acc)
                    );
                    break;

                case 7:

                    System.out.print("Account's number: ");
                    String historyAcc = scanner.nextLine();

                    bankService.getTransactionHistory(historyAcc)
                            .forEach(System.out::println);

                    break;

                case 0:
                    System.exit(0);

                default:
                    System.out.println("Incorrect menu item");
            }
        }
    }
}
