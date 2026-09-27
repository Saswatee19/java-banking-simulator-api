package com.bank.simulator.service;


import com.bank.simulator.entity.BankAccount;
import com.bank.simulator.entity.CheckingAccount;
import com.bank.simulator.entity.SavingsAccount;
import com.bank.simulator.repository.BankAccountRepository;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
//import org.springframework.retry.annotation.Backoff;
//import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BankAccountService {
    private final BankAccountRepository repository;

    // Constructor Injection (Spring automatically injects BankAccountRepository)
    public BankAccountService(BankAccountRepository repository) {
        this.repository = repository;
    }

    // --- CREATE ACCOUNTS ---
    public SavingsAccount createSavingsAccount(String accNum, String name, double initialDeposit, double interestRate) {
        SavingsAccount account = new SavingsAccount(accNum, name, initialDeposit, interestRate);
        return repository.save(account);
    }
    public CheckingAccount createCheckingAccount(String accNum, String name, double initialDeposit, double overdraftLimit) {
        CheckingAccount account = new CheckingAccount(accNum, name, initialDeposit, overdraftLimit);
        return repository.save(account);
    }

    // --- READ ACCOUNTS ---
    public BankAccount getAccount(String accountNumber) {
        return repository.findById(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + accountNumber));
    }

    public List<BankAccount> getAllAccounts() {
        return repository.findAll();
    }

    // --- TRANSACTIONS ---
    // Automatically retries 3 times if Optimistic Locking fails
//    @Retryable(
//            retryFor = { ObjectOptimisticLockingFailureException.class },
//            maxAttempts = 3,
//            backoff = @Backoff(delay = 100L) // waits 100ms between attempts
//    )
    @Transactional
    public BankAccount deposit(String accountNumber, double amount) {
        BankAccount account = getAccount(accountNumber);
        account.deposit(amount);
        return repository.save(account); // Persists updated balance and transaction history
    }

    @Transactional
    public BankAccount withdraw(String accountNumber, double amount) {
        BankAccount account = getAccount(accountNumber);
        account.withdraw(amount);
        return repository.save(account);
    }
}
