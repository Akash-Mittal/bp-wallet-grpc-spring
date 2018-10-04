package com.betpawa.wallet.client.dto;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.bp.wallet.proto.STATUS;

public final class WalletClientResponse {

    private Map<STATUS, AtomicLong> transactions;
    private Long timeTaken;

    public Map<STATUS, AtomicLong> getTransactions() {
        return transactions;
    }

    public void setTransactions(Map<STATUS, AtomicLong> transactions) {
        this.transactions = transactions;
    }

    public Long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public static class Builder {
        private Map<STATUS, AtomicLong> transactions;
        private Long timeTaken;

        public Builder transactions(Map<STATUS, AtomicLong> transactions) {
            this.transactions = transactions;
            return this;
        }

        public Builder timeTaken(Long timeTaken) {
            this.timeTaken = timeTaken;
            return this;
        }

        public WalletClientResponse build() {
            return new WalletClientResponse(this);
        }
    }

    private WalletClientResponse(Builder builder) {
        this.transactions = builder.transactions;
        this.timeTaken = builder.timeTaken;

    }
}
