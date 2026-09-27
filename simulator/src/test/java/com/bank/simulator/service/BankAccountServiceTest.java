package com.bank.simulator.service;

import com.bank.simulator.entity.SavingsAccount;
import com.bank.simulator.repository.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BankAccountServiceTest {

    @Mock
    private BankAccountRepository repository;

    @InjectMocks
    private BankAccountService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSavingsAccount_Success() {
        SavingsAccount account = new SavingsAccount("SAV-101", "Alice", 1000.0, 0.04);
        when(repository.save(any(SavingsAccount.class))).thenReturn(account);

        SavingsAccount created = service.createSavingsAccount("SAV-101", "Alice", 1000.0, 0.04);

        assertNotNull(created);
        assertEquals("SAV-101", created.getAccountNumber());
        assertEquals(1000.0, created.getBalance());
        verify(repository, times(1)).save(any(SavingsAccount.class));
    }

    @Test
    void testDeposit_Success() {
        SavingsAccount account = new SavingsAccount("SAV-101", "Alice", 1000.0, 0.04);
        when(repository.findById("SAV-101")).thenReturn(Optional.of(account));
        when(repository.save(any())).thenReturn(account);

        service.deposit("SAV-101", 500.0);

        assertEquals(1500.0, account.getBalance());
        verify(repository, times(1)).save(account);
    }

    @Test
    void testGetAccount_NotFound_ThrowsException() {
        when(repository.findById("UNKNOWN")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            service.getAccount("UNKNOWN");
        });
    }
}