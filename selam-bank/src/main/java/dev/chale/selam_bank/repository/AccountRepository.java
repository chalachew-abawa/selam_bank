package dev.chale.selam_bank.repository;

import dev.chale.selam_bank.model.Account;
import dev.chale.selam_bank.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByCustomer(Customer customer);
} 