package com.bp.wallet.server;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bp.wallet.proto.CURRENCY;
import com.bp.wallet.server.enity.Wallet;
import com.bp.wallet.server.enity.WalletPK;
import com.bp.wallet.server.repository.WalletRepository;

@SpringBootApplication
@EnableTransactionManagement
@EnableRetry
public class BPWalletServerApplication {

	private static final Logger log = LoggerFactory.getLogger(BPWalletServerApplication.class);

	@Autowired
	WalletRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(BPWalletServerApplication.class, args);
	}

	@Bean
	public CommandLineRunner buildData() {
		return (args) -> {
			repository.deleteAll();
			for (long i = 1; i <= 100; i++) {
				for (int j = 0; j < CURRENCY.values().length - 1; j++) {
					repository.save(new Wallet(new WalletPK(i, CURRENCY.forNumber(j)), BigDecimal.ZERO));
				}
			}
			log.info("Initialize DB with {} users ", 9999);
		};
	}
}
