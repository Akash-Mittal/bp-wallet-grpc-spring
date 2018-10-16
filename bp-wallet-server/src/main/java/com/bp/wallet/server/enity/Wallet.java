package com.bp.wallet.server.enity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "wallet")
public class Wallet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9189082357635718615L;

	@EmbeddedId
	private WalletPK walletPK;
	@NotNull
	private BigDecimal balance;
	@Version
	private Integer version;

	public Wallet() {
	}

	public WalletPK getWalletPK() {
		return walletPK;
	}

	public void setWalletPK(WalletPK walletPK) {
		this.walletPK = walletPK;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Wallet(WalletPK walletPK, @NotNull BigDecimal balance, Integer version) {
		super();
		this.walletPK = walletPK;
		this.balance = balance;
		this.version = version;
	}

	public Wallet(WalletPK walletPK, @NotNull BigDecimal balance) {
		super();
		this.walletPK = walletPK;
		this.balance = balance;
	}

	public static class Builder {
		private WalletPK walletPK;
		private BigDecimal balance;

		public Builder walletPK(WalletPK walletPK) {
			this.walletPK = walletPK;
			return this;
		}

		public Builder balance(BigDecimal balance) {
			this.balance = balance;
			return this;
		}

		public Wallet build() {
			return new Wallet(this);
		}
	}

	private Wallet(Builder builder) {
		this.walletPK = builder.walletPK;
		this.balance = builder.balance;
	}
}
