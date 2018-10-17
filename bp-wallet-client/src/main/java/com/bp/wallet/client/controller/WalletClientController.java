package com.bp.wallet.client.controller;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bp.wallet.client.dto.WalletClientRequest;
import com.bp.wallet.client.dto.WalletClientResponse;
import com.bp.wallet.client.service.WalletClientService;

@RestController
@RequestMapping("/")
public class WalletClientController {
	private static final Logger logger = LoggerFactory.getLogger(WalletClientController.class);

	@Autowired
	private WalletClientService walletClientService;

	@PostMapping
	public WalletClientResponse execute(@RequestBody WalletClientRequest walletClientRequest)
			throws InterruptedException {
		long time = System.currentTimeMillis();
		WalletClientResponse response = new WalletClientResponse.Builder()
				.transactions(walletClientService.run(walletClientRequest)).build();
		long timeTaken = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - time);
		logger.info("Excecution done time taken: {} {}", timeTaken, TimeUnit.SECONDS.name());
		response.setTimeTaken(timeTaken);
		return response;
	}
}
