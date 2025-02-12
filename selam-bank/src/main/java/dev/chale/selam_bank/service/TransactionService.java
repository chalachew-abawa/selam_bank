package dev.chale.selam_bank.service;

import dev.chale.selam_bank.model.Account;
import dev.chale.selam_bank.model.Transaction;
import dev.chale.selam_bank.repository.TransactionRepository;
import dev.chale.selam_bank.exception.InsufficientFundsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public List<Transaction> getAccountTransactions(Long accountId) {
        Account account = accountService.getAccountById(accountId);
        return transactionRepository.findByAccountOrderByTimestampDesc(account);
    }

    @Transactional
    public void deposit(Long accountId, BigDecimal amount, String description) {
        Account account = accountService.getAccountById(accountId);
        account.setBalance(account.getBalance().add(amount));
        
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setType("DEPOSIT");
        transaction.setAmount(amount);
        transaction.setDescription(description);
        
        transactionRepository.save(transaction);
    }

    @Transactional
    public void withdraw(Long accountId, BigDecimal amount, String description) {
        Account account = accountService.getAccountById(accountId);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        
        account.setBalance(account.getBalance().subtract(amount));
        
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setType("WITHDRAWAL");
        transaction.setAmount(amount.negate());
        transaction.setDescription(description);
        
        transactionRepository.save(transaction);
    }
} 