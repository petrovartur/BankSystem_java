package service;

import model.Account;
import model.Client;
import model.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BankService {

    private ArrayList<Client> clients = new ArrayList<>();
    private ArrayList<Account> accounts = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    private HashMap<String, Account> accountMap = new HashMap<>();

    private int accountCounter = 1001;
    private int transactionCounter = 1;

    public void addClient(Client client) {
        clients.add(client);
    }

    public void createAccount(int clientId, String type) {

        String accountNumber = "ACC-" + accountCounter++;

        Account account =
                new Account(accountNumber, clientId, 0, type);

        accounts.add(account);
        accountMap.put(accountNumber, account);

        System.out.println("Счёт создан: " + accountNumber);
    }

    public void deposit(String accountNumber, double amount) {

        if (amount <= 0) {
            System.out.println("Сумма должна быть больше нуля");
            return;
        }

        Account account = accountMap.get(accountNumber);

        if (account == null) {
            System.out.println("Счёт не найден");
            return;
        }

        account.setBalance(account.getBalance() + amount);

        transactions.add(
                new Transaction(
                        transactionCounter++,
                        accountNumber,
                        "DEPOSIT",
                        amount,
                        "Пополнение"
                )
        );
    }

    public void withdraw(String accountNumber, double amount) {

        if (amount <= 0) {
            System.out.println("Сумма должна быть больше нуля");
            return;
        }

        Account account = accountMap.get(accountNumber);

        if (account == null) {
            System.out.println("Счёт не найден");
            return;
        }

        if (account.getBalance() < amount) {
            System.out.println("Недостаточно средств");
            return;
        }

        account.setBalance(account.getBalance() - amount);

        transactions.add(
                new Transaction(
                        transactionCounter++,
                        accountNumber,
                        "WITHDRAW",
                        amount,
                        "Снятие средств"
                )
        );
    }

    public void transfer(String fromAccount,
                         String toAccount,
                         double amount) {

        if (amount <= 0) {
            System.out.println("Сумма должна быть больше нуля");
            return;
        }

        Account sender = accountMap.get(fromAccount);
        Account receiver = accountMap.get(toAccount);

        if (sender == null || receiver == null) {
            System.out.println("Один из счетов не найден");
            return;
        }

        if (sender.getBalance() < amount) {
            System.out.println("Недостаточно средств");
            return;
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        transactions.add(
                new Transaction(
                        transactionCounter++,
                        fromAccount,
                        "TRANSFER",
                        amount,
                        "Перевод на " + toAccount
                )
        );
    }

    public double getBalance(String accountNumber) {

        Account account = accountMap.get(accountNumber);

        if (account == null) {
            return 0;
        }

        return account.getBalance();
    }

    public List<Transaction> getTransactionHistory(String accountNumber) {

        List<Transaction> result = new ArrayList<>();

        for (Transaction t : transactions) {
            if (t.getAccountNumber().equals(accountNumber)) {
                result.add(t);
            }
        }

        return result;
    }

    public double getTotalBalanceByClient(int clientId) {

        double total = 0;

        for (Account account : accounts) {
            if (account.getClientId() == clientId) {
                total += account.getBalance();
            }
        }

        return total;
    }

    public List<Client> getClientsWithBalanceAbove(double amount) {

        List<Client> result = new ArrayList<>();

        for (Client client : clients) {
            if (getTotalBalanceByClient(client.getId()) > amount) {
                result.add(client);
            }
        }

        return result;
    }

    public Client getRichestClient() {

        Client richest = null;
        double maxBalance = 0;

        for (Client client : clients) {

            double balance =
                    getTotalBalanceByClient(client.getId());

            if (balance > maxBalance) {
                maxBalance = balance;
                richest = client;
            }
        }

        return richest;
    }
}
