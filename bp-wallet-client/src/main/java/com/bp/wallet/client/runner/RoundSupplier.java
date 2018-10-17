package com.bp.wallet.client.runner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.bp.wallet.client.enums.ROUND;
import com.bp.wallet.proto.BaseResponse;
import com.bp.wallet.proto.WalletServiceGrpc.WalletServiceFutureStub;
import com.google.common.util.concurrent.ListenableFuture;

@Component
@Scope("prototype")
public class RoundSupplier implements Supplier<List<ListenableFuture<BaseResponse>>> {

	@Autowired
	private WalletServiceFutureStub walletServiceFutureStub;

	@Autowired
	private TaskExecutor taskExecuter;

	private Long numberOfrounds;
	private String stats;
	private Long userID;

	@Override
	public List<ListenableFuture<BaseResponse>> get() {
		final List<ListenableFuture<BaseResponse>> round = new ArrayList<>();
		for (int i = 0; i < numberOfrounds; i++) {
			round.addAll(ROUND.values()[ThreadLocalRandom.current().nextInt(0, (ROUND.values().length))]
					.goExecute(walletServiceFutureStub, userID, taskExecuter));
		}
		return round;
	}

	public String getStats() {
		return stats;
	}

	public void setStats(String stats) {
		this.stats = stats;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public Long getNumberOfrounds() {
		return numberOfrounds;
	}

	public void setNumberOfrounds(Long numberOfrounds) {
		this.numberOfrounds = numberOfrounds;
	}

}
