package com.betpawa.wallet.client.service;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betpawa.wallet.client.dto.WalletClientRequest;
import com.betpawa.wallet.client.runner.UserSupplier;
import com.bp.wallet.proto.BaseResponse;
import com.bp.wallet.proto.STATUS;
import com.google.common.util.concurrent.ListenableFuture;

import io.grpc.Status;

@Service
public class WalletClientService {
	private static final Logger logger = LoggerFactory.getLogger(WalletClientService.class);

	@Autowired
	private UserSupplier userSupplier;

	public Map<STATUS, AtomicLong> run(final WalletClientRequest walletClientRequest) {

		final Map<STATUS, AtomicLong> statusMap = new EnumMap<STATUS, AtomicLong>(STATUS.class);
		statusMap.put(STATUS.TRANSACTION_SUCCESS, new AtomicLong(0));
		statusMap.put(STATUS.TRANSACTION_FAILED, new AtomicLong(0));

		final List<ListenableFuture<BaseResponse>> roundsLFResponse = new ArrayList<>();
		userSupplier.setWalletClientRequest(walletClientRequest);
		roundsLFResponse.addAll(userSupplier.get());
		roundsLFResponse.forEach(listenableFuture -> {
			try {
				BaseResponse response = listenableFuture.get();
				logger.info(roundsLFResponse.size() + ":  " + listenableFuture.get().getStatus().name(),
						listenableFuture.get().getStatusMessage());
				statusMap.get(STATUS.TRANSACTION_SUCCESS).incrementAndGet();
			} catch (Exception e) {
				logger.error(e.getMessage());
				if (e.getMessage().contains((Status.FAILED_PRECONDITION.getCode().name().toString()))) {
					statusMap.get(STATUS.TRANSACTION_SUCCESS).incrementAndGet();
				} else {
					statusMap.get(STATUS.TRANSACTION_FAILED).incrementAndGet();
				}
			}
		});
		return statusMap;
	}

}
