package com.bp.wallet.client.enums;

import java.math.BigDecimal;

public enum AMOUNT {

	ZERO(BigDecimal.valueOf(0)), HUNDRED(BigDecimal.valueOf(100.00)), TWOHUNDRED(BigDecimal.valueOf(200)),
	THREEHUNDRED(BigDecimal.valueOf(300)), FOURHUNDRED(BigDecimal.valueOf(400)), FIVEHUNDRED(BigDecimal.valueOf(500));

	private BigDecimal amount;

	public BigDecimal getAmount() {
		return amount;
	}

	private AMOUNT(BigDecimal amount) {
		this.amount = amount;
	}

}