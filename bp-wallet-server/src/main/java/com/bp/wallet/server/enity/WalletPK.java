package com.bp.wallet.server.enity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bp.wallet.proto.CURRENCY;

@Embeddable
public class WalletPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8849946668166588603L;
	@NotNull
	@Size(max = 20)
	private Long userID;

	@NotNull
	@Enumerated(EnumType.STRING)
	private CURRENCY currency;

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public WalletPK() {

	}

	public WalletPK(Long userID, CURRENCY currency) {
		super();
		this.userID = userID;
		this.currency = currency;
	}

	public CURRENCY getCurrency() {
		return currency;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WalletPK other = (WalletPK) obj;
		if (currency != other.currency)
			return false;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		return true;
	}

	public void setCurrency(CURRENCY currency) {
		this.currency = currency;
	}

	public static class Builder {
		private Long userID;
		private CURRENCY currency;

		public Builder userID(Long userID) {
			this.userID = userID;
			return this;
		}

		public Builder currency(CURRENCY currency) {
			this.currency = currency;
			return this;
		}

		public WalletPK build() {
			return new WalletPK(this);
		}
	}

	private WalletPK(Builder builder) {
		this.userID = builder.userID;
		this.currency = builder.currency;
	}
}