package com.bank.simulator.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bank_accounts")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BankAccount {

    @Id
    private String accountNumber;

    private String accountHolderName;

    protected double balance;

    @Version
    private Long version;

    @ElementCollection
    @CollectionTable(name = "transaction_history", joinColumns = @JoinColumn(name = "account_number"))
    @Column(name = "transaction_detail")
    private List<String> transactionHistory = new ArrayList<>();

    // default constructor
    public BankAccount() {}

    // param constructor
    public BankAccount(String accountNumber, String accountHolderName, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
        addTransaction(String.format("Initial deposit : %.2f", initialDeposit));
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
        }
        balance += amount;
        addTransaction(String.format("Deposited : %.2f", amount));
    }

    public abstract void withdraw(double amount);

    protected void addTransaction(String detail) {
        transactionHistory.add(detail);
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getAccountHolderName() {
        return accountHolderName;
    }
    public void setAccountHolderName(String accountHolderName) { this.accountHolderName = accountHolderName; }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) { this.balance = balance; }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public Long getVersion() {
        return version;
    }
}
