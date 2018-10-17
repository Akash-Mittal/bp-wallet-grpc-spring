package com.bp.wallet.client.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bp.wallet.client.dto.BalanceResponseDTO;
import com.bp.wallet.client.enums.AMOUNT;
import com.bp.wallet.client.enums.TRANSACTION;
import com.bp.wallet.client.BPWalletClientApplication;
import com.bp.wallet.proto.BaseRequest;
import com.bp.wallet.proto.BaseResponse;
import com.bp.wallet.proto.CURRENCY;
import com.bp.wallet.proto.STATUS;
import com.bp.wallet.proto.StatusMessage;
import com.bp.wallet.proto.WalletServiceGrpc.WalletServiceFutureStub;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BPWalletClientApplication.class)
@SpringBootTest
@Ignore
public class IntegrationTest {

    @Autowired
    private WalletServiceFutureStub futureStub;

    @Autowired
    private TaskExecutor taskExecuter;

    @Autowired
    private BalanceResponseDTO dto;

    private Long userID = 1L;

    @Before
    public void setUp() throws InterruptedException, ExecutionException {
        // This is Unnecessary- this is only a temporal fix to run integration tests.
        // Mocking needs to be done
        resetBalanceToZeroForTestUser();
    }

    @Test
    public void tests() throws InterruptedException, ExecutionException {
        t1();
        t2();
        t3();
        t4();
        t5();
        t6();
        t7();
        t8();
        t9();
        t10();
        t11();
        t12();
    }

    private void t1() throws InterruptedException {
        ListenableFuture<BaseResponse> futureResponse;
        BaseResponse response = null;
        futureResponse = TRANSACTION.WITHDRAW.doTransact(
                futureStub, BaseRequest.newBuilder().setUserID(userID)
                        .setAmount(AMOUNT.TWOHUNDERED.getAmount().toPlainString()).setCurrency(CURRENCY.USD).build(),
                taskExecuter);
        try {
            response = futureResponse.get();
        } catch (ExecutionException e) {
            assertThat(e.getMessage()).contains(StatusMessage.INSUFFICIENT_BALANCE.name());
        }
    }

    private void t2() throws InterruptedException, ExecutionException {
        ListenableFuture<BaseResponse> futureResponse;
        BaseResponse response;
        futureResponse = TRANSACTION.DEPOSIT.doTransact(
                futureStub, BaseRequest.newBuilder().setUserID(userID)
                        .setAmount(AMOUNT.HUNDERED.getAmount().toPlainString()).setCurrency(CURRENCY.USD).build(),
                taskExecuter);
        response = futureResponse.get();
        assertThat(response.getStatus().name()).contains(STATUS.TRANSACTION_SUCCESS.name());
    }

    private void t3() throws InterruptedException, ExecutionException {
        BaseResponse response = getBalanceForTestUser();
        BalanceResponseDTO dto = new Gson().fromJson(response.getStatusMessage(), BalanceResponseDTO.class);

        assertThat(new BigDecimal(dto.getBalance().get(CURRENCY.USD)).compareTo(AMOUNT.HUNDERED.getAmount()) == 0)
                .isTrue();

    }

    private void t4() throws InterruptedException {
        ListenableFuture<BaseResponse> futureResponse;
        BaseResponse response;
        futureResponse = TRANSACTION.WITHDRAW.doTransact(
                futureStub, BaseRequest.newBuilder().setUserID(userID)
                        .setAmount(AMOUNT.TWOHUNDERED.getAmount().toPlainString()).setCurrency(CURRENCY.USD).build(),
                taskExecuter);
        try {
            response = futureResponse.get();
        } catch (ExecutionException e) {
            assertThat(e.getMessage()).contains(StatusMessage.INSUFFICIENT_BALANCE.name());
        }
    }

    private void t5() throws InterruptedException, ExecutionException {
        ListenableFuture<BaseResponse> futureResponse;
        BaseResponse response;
        futureResponse = TRANSACTION.DEPOSIT.doTransact(
                futureStub, BaseRequest.newBuilder().setUserID(userID)
                        .setAmount(AMOUNT.HUNDERED.getAmount().toPlainString()).setCurrency(CURRENCY.EUR).build(),
                taskExecuter);
        response = futureResponse.get();
        assertThat(response.getStatus().name()).contains(STATUS.TRANSACTION_SUCCESS.name());
    }

    private void t6() throws InterruptedException, ExecutionException {
        BaseResponse response = getBalanceForTestUser();

        BalanceResponseDTO dto = new Gson().fromJson(response.getStatusMessage(), BalanceResponseDTO.class);

        assertThat(new BigDecimal(dto.getBalance().get(CURRENCY.USD)).compareTo(AMOUNT.HUNDERED.getAmount()) == 0)
                .isTrue();
        assertThat(new BigDecimal(dto.getBalance().get(CURRENCY.EUR)).compareTo(AMOUNT.HUNDERED.getAmount()) == 0)
                .isTrue();

    }

    private void t7() throws InterruptedException {
        ListenableFuture<BaseResponse> futureResponse;
        BaseResponse response;
        futureResponse = TRANSACTION.WITHDRAW.doTransact(
                futureStub, BaseRequest.newBuilder().setUserID(userID)
                        .setAmount(AMOUNT.TWOHUNDERED.getAmount().toPlainString()).setCurrency(CURRENCY.USD).build(),
                taskExecuter);
        try {
            response = futureResponse.get();
        } catch (ExecutionException e) {
            assertThat(e.getMessage()).contains(StatusMessage.INSUFFICIENT_BALANCE.name());
        }
    }

    private void t8() throws InterruptedException, ExecutionException {
        ListenableFuture<BaseResponse> futureResponse;
        BaseResponse response;
        futureResponse = TRANSACTION.DEPOSIT.doTransact(
                futureStub, BaseRequest.newBuilder().setUserID(userID)
                        .setAmount(AMOUNT.HUNDERED.getAmount().toPlainString()).setCurrency(CURRENCY.USD).build(),
                taskExecuter);
        response = futureResponse.get();
        assertThat(response.getStatus().name()).contains(STATUS.TRANSACTION_SUCCESS.name());
    }

    private void resetBalanceToZeroForTestUser() throws InterruptedException, ExecutionException {
        BaseResponse response = getBalanceForTestUser();
        BalanceResponseDTO dto = new Gson().fromJson(response.getStatusMessage(), BalanceResponseDTO.class);
        withdrawAllFromTestUserWallet(dto);
    }

    private void t9() throws InterruptedException, ExecutionException {
        BaseResponse response = getBalanceForTestUser();
        BalanceResponseDTO dto = new Gson().fromJson(response.getStatusMessage(), BalanceResponseDTO.class);

        assertThat(new BigDecimal(dto.getBalance().get(CURRENCY.USD)).compareTo(AMOUNT.TWOHUNDERED.getAmount()) == 0)
                .isTrue();
        assertThat(new BigDecimal(dto.getBalance().get(CURRENCY.EUR)).compareTo(AMOUNT.HUNDERED.getAmount()) == 0)
                .isTrue();

    }

    private void t10() throws InterruptedException, ExecutionException {
        ListenableFuture<BaseResponse> futureResponse;
        BaseResponse response;
        futureResponse = TRANSACTION.WITHDRAW.doTransact(
                futureStub, BaseRequest.newBuilder().setUserID(userID)
                        .setAmount(AMOUNT.TWOHUNDERED.getAmount().toPlainString()).setCurrency(CURRENCY.USD).build(),
                taskExecuter);
        response = futureResponse.get();
        assertThat(response.getStatus().name()).contains(STATUS.TRANSACTION_SUCCESS.name());

    }

    private void t11() throws InterruptedException, ExecutionException {
        BaseResponse response = getBalanceForTestUser();
        BalanceResponseDTO dto = new Gson().fromJson(response.getStatusMessage(), BalanceResponseDTO.class);

        assertThat(new BigDecimal(dto.getBalance().get(CURRENCY.USD)).compareTo(AMOUNT.ZERO.getAmount()) == 0).isTrue();
        assertThat(new BigDecimal(dto.getBalance().get(CURRENCY.EUR)).compareTo(AMOUNT.HUNDERED.getAmount()) == 0)
                .isTrue();

    }

    private void t12() throws InterruptedException {
        ListenableFuture<BaseResponse> futureResponse;
        BaseResponse response;
        futureResponse = TRANSACTION.WITHDRAW.doTransact(
                futureStub, BaseRequest.newBuilder().setUserID(userID)
                        .setAmount(AMOUNT.TWOHUNDERED.getAmount().toPlainString()).setCurrency(CURRENCY.USD).build(),
                taskExecuter);
        try {
            response = futureResponse.get();
        } catch (ExecutionException e) {
            assertThat(e.getMessage()).contains(StatusMessage.INSUFFICIENT_BALANCE.name());
        }
    }

    private void withdrawAllFromTestUserWallet(BalanceResponseDTO dto) {
        dto.getBalance().entrySet().stream().filter(es -> new BigDecimal(es.getValue()).compareTo(BigDecimal.ZERO) > 0

        ).forEach(es -> {
            try {
                TRANSACTION.WITHDRAW.doTransact(futureStub, BaseRequest.newBuilder().setUserID(userID)
                        .setAmount(es.getValue()).setCurrency(es.getKey()).build(), taskExecuter).get();
            } catch (InterruptedException | ExecutionException e) {
            }

        });
    }

    private BaseResponse getBalanceForTestUser() throws InterruptedException, ExecutionException {
        ListenableFuture<BaseResponse> futureResponse;
        BaseResponse response;
        futureResponse = TRANSACTION.BALANCE.doTransact(futureStub, BaseRequest.newBuilder().setUserID(userID).build(),
                taskExecuter);
        response = futureResponse.get();
        return response;
    }

}
