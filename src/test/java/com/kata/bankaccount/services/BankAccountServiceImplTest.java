package com.kata.bankaccount.services;

import com.kata.bankaccount.exceptions.BankAccountException;
import com.kata.bankaccount.models.BankAccount;
import com.kata.bankaccount.services.BankAccountService;
import com.kata.bankaccount.services.BankAccountServiceImpl;
import com.kata.bankaccount.services.StorageService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class BankAccountServiceImplTest {

    BankAccountService bankAccountService;

    @Mock
    StorageService storageService;

    private final ByteArrayOutputStream tempOut = new ByteArrayOutputStream();
    private final PrintStream sysOut = System.out;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        bankAccountService = new BankAccountServiceImpl(storageService);
        System.setOut(new PrintStream(tempOut));
    }

    @After
    public void clean() {
        System.setOut(sysOut);
    }

    @Test(expected = BankAccountException.class)
    public void testDeposit_ShouldHandleInvalidAmount() throws BankAccountException {
        bankAccountService.deposit(123L, new BigDecimal(-150));
    }

    @Test(expected = BankAccountException.class)
    public void testDeposit_ShouldHandleInvalidAccount() throws BankAccountException {
        when(storageService.getBankAccountById(any(Long.class))).thenReturn(null);

        bankAccountService.deposit(123L, new BigDecimal(130));
    }

    @Test
    public void testDeposit_OK() throws BankAccountException {
        BankAccount account = new BankAccount();
        when(storageService.getBankAccountById(any(Long.class))).thenReturn(account);

        BigDecimal amount = new BigDecimal("350.75");
        bankAccountService.deposit(123L, amount);

        assertEquals(amount, account.getBalance());
        assertEquals(1, account.getStatements().size());
    }

    @Test(expected = BankAccountException.class)
    public void testWithdraw_ShouldHandleInvalidAmount() throws BankAccountException {
        bankAccountService.withdraw(123L, new BigDecimal(-100));
    }

    @Test(expected = BankAccountException.class)
    public void testWithdraw_ShouldHandleInvalidAccount() throws BankAccountException {
        when(storageService.getBankAccountById(any(Long.class))).thenReturn(null);

        bankAccountService.withdraw(4858L, new BigDecimal(50));
    }

    @Test
    public void testWithdraw_OK() throws BankAccountException {
        BankAccount account = new BankAccount();
        when(storageService.getBankAccountById(any(Long.class))).thenReturn(account);

        bankAccountService.deposit(123L, new BigDecimal("500"));
        bankAccountService.withdraw(123L, new BigDecimal("30"));
        bankAccountService.withdraw(123L, new BigDecimal("150.25"));

        assertEquals(new BigDecimal("319.75"), account.getBalance());
        assertEquals(3, account.getStatements().size());
    }

    @Test(expected = BankAccountException.class)
    public void testSeeHistory_ShouldHandleInvalidAccount() throws BankAccountException {
        when(storageService.getBankAccountById(any(Long.class))).thenReturn(null);

        bankAccountService.seeHistory(159L);
    }

    @Test
    public void testSeeHistory_ShouldHandleStatements() throws BankAccountException {
        BankAccount account = new BankAccount();
        when(storageService.getBankAccountById(any(Long.class))).thenReturn(account);

        bankAccountService.deposit(123L, new BigDecimal("500"));
        bankAccountService.withdraw(123L, new BigDecimal("30"));
        bankAccountService.withdraw(123L, new BigDecimal("150.25"));

        bankAccountService.seeHistory(123L);

        assertEquals(new BigDecimal("319.75"), account.getBalance());
        assertEquals(3, account.getStatements().size());
        assertTrue(tempOut.toString().contains("Operation\t\tDate\t\tAmount\t\tBalance\n"));
    }

    @Test
    public void testSeeHistory_ShouldHandleNoStatements() throws BankAccountException {
        when(storageService.getBankAccountById(any(Long.class))).thenReturn(new BankAccount());

        bankAccountService.seeHistory(123L);
        assertEquals("No operation found.", tempOut.toString());
    }
}
