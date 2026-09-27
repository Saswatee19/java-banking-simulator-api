package com.bank.simulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bank.simulator.entity.BankAccount;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    // Spring Data JPA automatically provides standard methods like save(), findById(), findAll(), deleteById()!
}
