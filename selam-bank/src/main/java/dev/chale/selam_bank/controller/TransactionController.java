package dev.chale.selam_bank.controller;

import dev.chale.selam_bank.service.TransactionService;
import dev.chale.selam_bank.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import org.springframework.ui.Model;
import java.util.List;
import java.util.Comparator;
import dev.chale.selam_bank.model.Account;
import dev.chale.selam_bank.model.Transaction;

@Controller
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final AccountService accountService;

    @PostMapping
    public String processTransaction(@RequestParam Long accountId,
                                   @RequestParam String type,
                                   @RequestParam BigDecimal amount,
                                   @RequestParam String description,
                                   RedirectAttributes redirectAttributes) {
        try {
            if ("deposit".equalsIgnoreCase(type)) {
                transactionService.deposit(accountId, amount, description);
                redirectAttributes.addFlashAttribute("success", "Deposit successful");
            } else if ("withdraw".equalsIgnoreCase(type)) {
                transactionService.withdraw(accountId, amount, description);
                redirectAttributes.addFlashAttribute("success", "Withdrawal successful");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/transfer")
    public String processTransfer(@RequestParam Long fromAccountId,
                                @RequestParam Long toAccountId,
                                @RequestParam BigDecimal amount,
                                @RequestParam String description,
                                RedirectAttributes redirectAttributes) {
        try {
            transactionService.transfer(fromAccountId, toAccountId, amount, description);
            redirectAttributes.addFlashAttribute("success", "Transfer successful");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/dashboard";
    }

    @GetMapping
    public String showTransactions(Model model) {
        List<Account> accounts = accountService.getCurrentUserAccounts();
        model.addAttribute("accounts", accounts);
        
        // Get transactions for all accounts
        List<Transaction> allTransactions = accounts.stream()
            .flatMap(account -> transactionService.getAccountTransactions(account.getId()).stream())
            .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
            .toList();
            
        model.addAttribute("transactions", allTransactions);
        return "transactions";
    }

    @GetMapping("/new")
    public String showNewTransactionForm(Model model) {
        model.addAttribute("accounts", accountService.getCurrentUserAccounts());
        return "transaction-form";
    }
} 