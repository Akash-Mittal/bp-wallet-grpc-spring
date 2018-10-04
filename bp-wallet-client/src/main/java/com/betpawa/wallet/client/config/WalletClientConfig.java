package com.betpawa.wallet.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bp.wallet.proto.WalletServiceGrpc;
import com.bp.wallet.proto.WalletServiceGrpc.WalletServiceFutureStub;

import io.grpc.Channel;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;

@Configuration
public class WalletClientConfig {
	@GrpcClient("bp-wallet-server")
	private Channel serverChannel;

	@Bean
	WalletServiceFutureStub getWalletServiceFutureStub() {
		return WalletServiceGrpc.newFutureStub(serverChannel);
	}
}
