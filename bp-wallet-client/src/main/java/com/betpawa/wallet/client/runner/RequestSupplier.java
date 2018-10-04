package com.betpawa.wallet.client.runner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.betpawa.wallet.client.dto.WalletClientRequest;
import com.bp.wallet.proto.BaseResponse;
import com.google.common.util.concurrent.ListenableFuture;

@Component
@Scope("prototype")
public class RequestSupplier implements Supplier<List<ListenableFuture<BaseResponse>>> {

    @Autowired
    private RoundSupplier roundSupplier;

    private WalletClientRequest walletClientRequest;
    private Long userID;

    @Override
    public List<ListenableFuture<BaseResponse>> get() {
        final List<ListenableFuture<BaseResponse>> round = new ArrayList<>();
        roundSupplier.setNumberOfrounds(walletClientRequest.getNumberOfRounds());
        roundSupplier.setUserID(userID);
        for (int i = 0; i < walletClientRequest.getNumberOfRequests(); i++) {
            round.addAll(roundSupplier.get());
        }
        return round;
    }

    public WalletClientRequest getWalletClientRequest() {
        return walletClientRequest;
    }

    public void setWalletClientRequest(WalletClientRequest walletClientRequest) {
        this.walletClientRequest = walletClientRequest;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

}
