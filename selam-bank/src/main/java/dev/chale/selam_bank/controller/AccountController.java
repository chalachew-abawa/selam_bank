package dev.chale.selam_bank.controller;

import dev.chale.selam_bank.model.Account;
import dev.chale.selam_bank.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import lombok.AllArgsConstructor;
import java.util.List;

@Controller
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    public String listAccounts(Model model) {
        List<Account> accounts = accountService.getCurrentUserAccounts();
        model.addAttribute("accounts", accounts);
        return "accounts";
    }

    @PostMapping("/create")
    public String createAccount(@RequestParam String accountType, RedirectAttributes redirectAttributes) {
        try {
            accountService.createAccount(accountType);
            redirectAttributes.addFlashAttribute("success", "Account created successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/accounts";
    }

    @GetMapping("/{id}")
    public String getAccountDetails(@PathVariable Long id, Model model) {
        Account account = accountService.getAccountById(id);
        model.addAttribute("account", account);
        return "account-details";
    }
} 