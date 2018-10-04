package com.betpawa.wallet.client.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

import com.bp.wallet.proto.BaseRequest;
import com.bp.wallet.proto.BaseResponse;
import com.bp.wallet.proto.WalletServiceGrpc.WalletServiceFutureStub;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import io.grpc.Status;

public enum TRANSACTION {

    DEPOSIT {
        @Override
        public ListenableFuture<BaseResponse> doTransact(final WalletServiceFutureStub futureStub,
                final BaseRequest baseRequest, final TaskExecutor taskExecutor) {

            ListenableFuture<BaseResponse> response = futureStub.deposit(baseRequest);
            Futures.addCallback(response, new FutureCallback<BaseResponse>() {
                @Override
                public void onSuccess(BaseResponse result) {
                    logger.info("{} {}", result.getStatus().name(), result.getStatusMessage());
                }

                @Override
                public void onFailure(Throwable t) {
                    logger.warn(Status.fromThrowable(t).getDescription());
                }
            }, taskExecutor);

            return response;
        }
    },
    WITHDRAW {
        @Override
        public ListenableFuture<BaseResponse> doTransact(final WalletServiceFutureStub futureStub,
                final BaseRequest baseRequest, final TaskExecutor taskExecutor) {
            ListenableFuture<BaseResponse> response = futureStub.withdraw(baseRequest);

            Futures.addCallback(response, new FutureCallback<BaseResponse>() {
                @Override
                public void onSuccess(BaseResponse result) {
                    logger.info("{} {}", result.getStatus().name(), result.getStatusMessage());
                }

                @Override
                public void onFailure(Throwable t) {
                    logger.warn(Status.fromThrowable(t).getDescription());
                }
            }, taskExecutor);
            return response;

        }
    },
    BALANCE {
        @Override
        public ListenableFuture<BaseResponse> doTransact(final WalletServiceFutureStub futureStub,
                final BaseRequest baseRequest, final TaskExecutor taskExecutor) {
            ListenableFuture<BaseResponse> response = futureStub.balance(baseRequest);

            Futures.addCallback(response, new FutureCallback<BaseResponse>() {
                @Override
                public void onSuccess(BaseResponse result) {
                    logger.info("{} {}", result.getStatus().name(), result.getStatusMessage());
                }

                @Override
                public void onFailure(Throwable t) {
                    logger.warn(Status.fromThrowable(t).getDescription());
                }
            }, taskExecutor);
            return response;

        }
    };

    public abstract ListenableFuture<BaseResponse> doTransact(final WalletServiceFutureStub futureStub,
            final BaseRequest baseRequest, final TaskExecutor taskExecutor);

    private static final Logger logger = LoggerFactory.getLogger(TRANSACTION.class);

}