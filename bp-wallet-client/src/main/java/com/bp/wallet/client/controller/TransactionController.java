package com.bp.wallet.client.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bp.wallet.client.enums.TRANSACTION;
import com.bp.wallet.proto.BaseRequest;
import com.bp.wallet.proto.CURRENCY;
import com.bp.wallet.proto.STATUS;
import com.bp.wallet.proto.WalletServiceGrpc.WalletServiceFutureStub;

@RestController
@RequestMapping("/tx")
public class TransactionController {
	@Autowired
	private WalletServiceFutureStub walletServiceFutureStub;

	@Autowired
	private TaskExecutor taskExecuter;

	@GetMapping(path = "/deposit")
	public STATUS deposit(@RequestParam(value = "userid") Long userID, @RequestParam(value = "amount") String amount,
			@RequestParam(value = "currency") CURRENCY currency) throws InterruptedException, ExecutionException {

		return TRANSACTION.DEPOSIT.doTransact(walletServiceFutureStub,
				BaseRequest.newBuilder().setUserID(userID).setAmount(amount).setCurrency(currency).build(),
				taskExecuter).get().getStatus();
	}

	@GetMapping(path = "/withdraw")
	public STATUS withdraw(@RequestParam(value = "userid") Long userID, @RequestParam(value = "amount") String amount,
			@RequestParam(value = "currency") CURRENCY currency) throws InterruptedException, ExecutionException {

		return TRANSACTION.WITHDRAW.doTransact(walletServiceFutureStub,
				BaseRequest.newBuilder().setUserID(userID).setAmount(amount).setCurrency(currency).build(),
				taskExecuter).get().getStatus();
	}

	@GetMapping(path = "/balance")
	public String withdraw(@RequestParam(value = "userid") Long userID)
			throws InterruptedException, ExecutionException {
		return TRANSACTION.BALANCE
				.doTransact(walletServiceFutureStub, BaseRequest.newBuilder().setUserID(userID).build(), taskExecuter)
				.get().getStatusMessage();
	}
}
