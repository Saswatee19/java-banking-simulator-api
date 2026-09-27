package com.bank.simulator.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "checking_accounts")
public class CheckingAccount extends BankAccount {

    private double overdraftLimit;

    //default constructor
    public CheckingAccount() {}

    //param constructor
    public CheckingAccount(String accountNumber, String accountHolderName, double initialDeposit, double overdraftLimit) {
        super(accountNumber, accountHolderName, initialDeposit);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount requested.");
        }
        if (balance - amount < -overdraftLimit) {
            System.out.println("Transaction declined: Exceeds overdraft limit of $" + overdraftLimit);
        }
        balance -= amount;
        addTransaction(String.format("Withdrew : $%.2f", amount));
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }
    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }
}