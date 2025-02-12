package dev.chale.selam_bank.controller;

import dev.chale.selam_bank.model.Customer;
import dev.chale.selam_bank.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfileController {
    private final CustomerService customerService;

    @GetMapping
    public String showProfile(Model model) {
        Customer customer = customerService.getCurrentUser();
        model.addAttribute("customer", customer);
        return "profile";
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes) {
        try {
            customerService.updateProfile(customer);
            redirectAttributes.addFlashAttribute("success", "Profile updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/profile";
    }
} 