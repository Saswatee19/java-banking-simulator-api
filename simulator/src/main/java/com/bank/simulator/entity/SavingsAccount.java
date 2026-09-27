package com.bank.simulator.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "savings_accounts")
public class SavingsAccount extends BankAccount {

    private double interestRate;
    private static final double MIN_BALANCE = 100.0;

    //default constructor
    public SavingsAccount() {}

    //param constructor
    public SavingsAccount(String accountNumber, String accountHolderName, double initialDeposit, double interestRate) {
        super(accountNumber, accountHolderName, initialDeposit);
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount requested.");
        }
        if (balance - amount < MIN_BALANCE) {
            System.out.println("Transaction declined. Minimum balance of $" + MIN_BALANCE + " is required.");
        }
        balance -= amount;
        addTransaction(String.format("Withdrew : $%.2f", amount));
    }

    public void applyInterest() {
        double interest = balance * interestRate;
        balance += interest;
        addTransaction(String.format("Interest Applied : $%.2f", interest));
    }

    public double getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }
}
