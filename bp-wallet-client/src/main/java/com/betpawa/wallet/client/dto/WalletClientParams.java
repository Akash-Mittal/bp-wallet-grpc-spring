package com.betpawa.wallet.client.dto;

import java.util.concurrent.ExecutorService;

import com.bp.wallet.proto.WalletServiceGrpc.WalletServiceFutureStub;

public final class WalletClientParams {

	private final Integer numberOfUsers;
	private final Integer numberOfRequests;
	private final Integer numberOfRounds;
	private final WalletServiceFutureStub futureStub;
	private final ExecutorService pool;

	public Integer getNumberOfUsers() {
		return numberOfUsers;
	}

	public WalletClientParams(Integer numberOfUsers, Integer numberOfRequests, Integer numberOfRounds,
			WalletServiceFutureStub futureStub, ExecutorService pool) {
		super();
		this.numberOfUsers = numberOfUsers;
		this.numberOfRequests = numberOfRequests;
		this.numberOfRounds = numberOfRounds;
		this.futureStub = futureStub;
		this.pool = pool;
	}

	public Integer getNumberOfRequests() {
		return numberOfRequests;
	}

	public Integer getNumberOfRounds() {
		return numberOfRounds;
	}

	public WalletServiceFutureStub getFutureStub() {
		return futureStub;
	}

	public ExecutorService getPool() {
		return pool;
	}

	@Override
	public String toString() {
		return "ClientParams [numberOfUsers=" + numberOfUsers + ", numberOfRequests=" + numberOfRequests
				+ ", numberOfRounds=" + numberOfRounds;
	}
}
