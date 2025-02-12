package dev.chale.selam_bank.service;

import dev.chale.selam_bank.model.Account;
import dev.chale.selam_bank.model.Customer;
import dev.chale.selam_bank.repository.AccountRepository;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;

    public List<Account> getCurrentUserAccounts() {
        Customer currentUser = customerService.getCurrentUser();
        return accountRepository.findByCustomer(currentUser);
    }

    public void createAccount(String accountType) {
        Customer currentUser = customerService.getCurrentUser();
        Account account = new Account();
        account.setAccountType(accountType);
        account.setAccountNumber(generateAccountNumber());
        account.setCustomer(currentUser);
        account.setBalance(BigDecimal.ZERO);
        accountRepository.save(account);
    }

    private String generateAccountNumber() {
        return String.format("%010d", System.currentTimeMillis() % 10000000000L);
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Account not found"));
    }
} 