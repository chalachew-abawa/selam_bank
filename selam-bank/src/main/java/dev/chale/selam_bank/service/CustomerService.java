package dev.chale.selam_bank.service;

import dev.chale.selam_bank.model.Customer;
import dev.chale.selam_bank.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;

@Service
@AllArgsConstructor
public class CustomerService implements UserDetailsService {
    private static final String USER_NOT_FOUND_MSG = "User not found";
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MSG));
        
        return User.builder()
            .username(customer.getUsername())
            .password(customer.getPassword())
            .roles("USER")
            .build();
    }

    public void register(Customer customer) {
        // Encode password before saving
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);
    }

    public boolean authenticate(String username, String rawPassword) {
        Customer customer = customerRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MSG));
        
        // Compare raw password with encoded password
        return passwordEncoder.matches(rawPassword, customer.getPassword());
    }

    public Customer getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return customerRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MSG));
    }

    public void updateProfile(Customer updatedCustomer) {
        Customer currentUser = getCurrentUser();
        currentUser.setFirstName(updatedCustomer.getFirstName());
        currentUser.setLastName(updatedCustomer.getLastName());
        currentUser.setEmail(updatedCustomer.getEmail());
        customerRepository.save(currentUser);
    }
} 