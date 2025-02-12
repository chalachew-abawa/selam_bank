package dev.chale.selam_bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("dev.chale.selam_bank.model")
@EnableJpaRepositories("dev.chale.selam_bank.repository")
public class SelamBankApplication {
	public static void main(String[] args) {
		SpringApplication.run(SelamBankApplication.class, args);
	}
}
