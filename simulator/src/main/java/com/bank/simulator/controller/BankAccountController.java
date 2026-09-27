package com.bank.simulator.controller;

import com.bank.simulator.entity.BankAccount;
import com.bank.simulator.entity.CheckingAccount;
import com.bank.simulator.entity.SavingsAccount;
import com.bank.simulator.service.BankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    private final BankAccountService service;

    public BankAccountController(BankAccountService service) {
        this.service = service;
    }

    // --- CREATE SAVINGS ACCOUNT ---
    // POST http://localhost:8080/api/accounts/savings?accNum=SAV-101&name=Alice&initialDeposit=500&interestRate=0.04
    @PostMapping("/savings")
    public ResponseEntity<SavingsAccount> createSavingsAccount(
            @RequestParam String accNum,  //http://localhost:8080/api/accounts/savings?accNum=SAV-101&name=xyzname&initialDeposit=10000 -->this is how @RequestParam works expects u to do givee details through url
            @RequestParam String name,    //industry standard is @RequestBody for JSON req, res
            @RequestParam double initialDeposit,
            @RequestParam(defaultValue = "0.03") double interestRate) {

        SavingsAccount account = service.createSavingsAccount(accNum, name, initialDeposit, interestRate);
        return ResponseEntity.ok(account);
    }

    // --- CREATE CHECKING ACCOUNT ---
    // POST http://localhost:8080/api/accounts/checking?accNum=CHK-202&name=Bob&initialDeposit=200&overdraftLimit=300
    @PostMapping("/checking")
    public ResponseEntity<CheckingAccount> createCheckingAccount(
            @RequestParam String accNum,
            @RequestParam String name,
            @RequestParam double initialDeposit,
            @RequestParam(defaultValue = "500.0") double overdraftLimit) {

        CheckingAccount account = service.createCheckingAccount(accNum, name, initialDeposit, overdraftLimit);
        return ResponseEntity.ok(account);
    }

    // --- GET ALL ACCOUNTS ---
    // GET http://localhost:8080/api/accounts
    @GetMapping
    public ResponseEntity<List<BankAccount>> getAllAccounts() {
        return ResponseEntity.ok(service.getAllAccounts());
    }

    // --- GET ACCOUNT BY ID ---
    // GET http://localhost:8080/api/accounts/SAV-101
    @GetMapping("/{accNum}")
    public ResponseEntity<BankAccount> getAccount(@PathVariable String accNum) {
        return ResponseEntity.ok(service.getAccount(accNum));
    }

    // --- DEPOSIT MONEY ---
    // POST http://localhost:8080/api/accounts/SAV-101/deposit?amount=150
    @PostMapping("/{accNum}/deposit")
    public ResponseEntity<BankAccount> deposit(
            @PathVariable String accNum,
            @RequestParam double amount) {

        return ResponseEntity.ok(service.deposit(accNum, amount));
    }

    // --- WITHDRAW MONEY ---
    // POST http://localhost:8080/api/accounts/SAV-101/withdraw?amount=50
    @PostMapping("/{accNum}/withdraw")
    public ResponseEntity<BankAccount> withdraw(
            @PathVariable String accNum,
            @RequestParam double amount) {

        return ResponseEntity.ok(service.withdraw(accNum, amount));
    }
}