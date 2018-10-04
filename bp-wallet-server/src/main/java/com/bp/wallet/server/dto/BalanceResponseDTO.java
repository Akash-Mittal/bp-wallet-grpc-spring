package com.bp.wallet.server.dto;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bp.wallet.proto.CURRENCY;
import com.bp.wallet.server.enity.Wallet;
import com.google.gson.Gson;

@Component
@Scope("prototype")
public class BalanceResponseDTO {
	private Map<CURRENCY, String> balance;

	public Map<CURRENCY, String> getBalance() {
		if (balance == null) {
			balance = new EnumMap<>(CURRENCY.class);
			Arrays.stream(CURRENCY.values()).filter(checkInvalidCurrency()).forEach(currency -> {
				balance.put(currency, "0");
			});
		}
		return balance;
	}

	public void setBalance(Map<CURRENCY, String> balance) {
		this.balance = balance;
	}

	public String getBalanceAsString(final Optional<List<Wallet>> userWallets) {
		userWallets.get().stream().filter(checkInvalidCurrencyFromWallet()).forEach(wallet -> {
			this.getBalance().put(wallet.getWalletPK().getCurrency(), wallet.getBalance().toPlainString());
		});

		return new Gson().toJson(this);
	}

	public BalanceResponseDTO getBalanceResponseDTOFromString(String response) {
		return new Gson().fromJson(response, BalanceResponseDTO.class);
	}

	private Predicate<? super Wallet> checkInvalidCurrencyFromWallet() {
		return wallet -> wallet.getWalletPK().getCurrency() != CURRENCY.UNRECOGNIZED;
	}

	private Predicate<? super CURRENCY> checkInvalidCurrency() {
		return currency -> currency != CURRENCY.UNRECOGNIZED;
	}

}
