package com.bp.wallet.client.dto;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bp.wallet.proto.CURRENCY;

@Component
@Scope("prototype")
public class BalanceResponseDTO {
	private Map<CURRENCY, String> balance;

	public Map<CURRENCY, String> getBalance() {
		if (balance == null) {
			balance = new EnumMap<>(CURRENCY.class);
			Arrays.stream(CURRENCY.values()).forEach(currency -> {
				balance.put(currency, "0");
			});
		}
		return balance;
	}

	public void setBalance(Map<CURRENCY, String> balance) {
		this.balance = balance;
	}

}
