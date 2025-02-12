package dev.chale.selam_bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import dev.chale.selam_bank.model.Customer;
import dev.chale.selam_bank.service.CustomerService;

@Controller
@AllArgsConstructor
public class MainController {
    private final CustomerService customerService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Customer customer = customerService.getCurrentUser();
        model.addAttribute("customer", customer);
        return "dashboard";
    }
} 