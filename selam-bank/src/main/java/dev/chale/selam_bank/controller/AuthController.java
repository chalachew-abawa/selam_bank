package dev.chale.selam_bank.controller;

import dev.chale.selam_bank.model.Customer;
import dev.chale.selam_bank.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private static final String LOGIN_REDIRECT = "redirect:/auth/login";
    private static final String ERROR_KEY = "error";
    private final CustomerService customerService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(Customer customer, RedirectAttributes redirectAttributes) {
        try {
            customerService.register(customer);
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return LOGIN_REDIRECT;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(ERROR_KEY, e.getMessage());
            return "redirect:/auth/register";
        }
    }

    @PostMapping("/login")
    public String login(String username, String password, RedirectAttributes redirectAttributes) {
        try {
            if (customerService.authenticate(username, password)) {
                return "redirect:/dashboard";
            }
            redirectAttributes.addFlashAttribute(ERROR_KEY, "Invalid credentials");
            return LOGIN_REDIRECT;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(ERROR_KEY, e.getMessage());
            return LOGIN_REDIRECT;
        }
    }
} 