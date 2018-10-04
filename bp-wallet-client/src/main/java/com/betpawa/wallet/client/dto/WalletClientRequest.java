package com.betpawa.wallet.client.dto;

public final class WalletClientRequest {

	private Long numberOfUsers;
	private Long numberOfRequests;
	private Long numberOfRounds;

	public Long getNumberOfUsers() {
		return numberOfUsers;
	}

	public void setNumberOfUsers(Long numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}

	public Long getNumberOfRequests() {
		return numberOfRequests;
	}

	public void setNumberOfRequests(Long numberOfRequests) {
		this.numberOfRequests = numberOfRequests;
	}

	public Long getNumberOfRounds() {
		return numberOfRounds;
	}

	public void setNumberOfRounds(Long numberOfRounds) {
		this.numberOfRounds = numberOfRounds;
	}
}
