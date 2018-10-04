// package com.bp.wallet.server.service;
//
// import org.junit.Before;
// import org.junit.Ignore;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.junit4.SpringRunner;
//
// import com.bp.wallet.proto.BalanceRequest;
// import com.bp.wallet.proto.BalanceResponse;
// import com.bp.wallet.proto.CURRENCY;
// import com.bp.wallet.proto.DepositRequest;
// import com.bp.wallet.proto.DepositResponse;
// import com.bp.wallet.proto.WithdrawRequest;
// import com.bp.wallet.proto.WithdrawResponse;
// import com.bp.wallet.server.config.TestConfiguration;
// import com.bp.wallet.server.repository.WalletRepository;
//
// import io.grpc.stub.StreamObserver;
//
// @RunWith(SpringRunner.class)
// @SpringBootTest(classes = TestConfiguration.class)
// @Ignore
// public class WalletServerServiceTest {
// private static final Logger logger = LoggerFactory.getLogger(WalletServerServiceTest.class);
//
// @Autowired
// WalletServerService service;
// @Autowired
// WalletRepository repository;
//
// @Before
// public void setUp() throws Exception {
// repository.deleteAll();
// }
//
// @Test
// public void testAllOperations() throws InterruptedException {
// StreamObserver<DepositResponse> responseObserver = new StreamObserver<DepositResponse>() {
// @Override
// public void onNext(DepositResponse value) {
// logger.debug("onnext");
// }
//
// @Override
// public void onError(Throwable t) {
// logger.debug("error");
//
// }
//
// @Override
// public void onCompleted() {
// logger.debug("completed");
// }
// };
//
// StreamObserver<WithdrawResponse> withdrawResponseResponseObserver = new StreamObserver<WithdrawResponse>() {
// @Override
// public void onNext(WithdrawResponse value) {
// logger.debug("onnext");
// }
//
// @Override
// public void onError(Throwable t) {
// logger.debug("error");
//
// }
//
// @Override
// public void onCompleted() {
// logger.debug("completed");
// }
// };
//
// StreamObserver<BalanceResponse> balanceResponseResponseObserver = new StreamObserver<BalanceResponse>() {
// @Override
// public void onNext(BalanceResponse value) {
// logger.debug("onnext");
// }
//
// @Override
// public void onError(Throwable t) {
// logger.debug("error");
//
// }
//
// @Override
// public void onCompleted() {
// logger.debug("completed");
// }
// };
// service.deposit(DepositRequest.newBuilder().setAmount("1000").setUserID(1L).setCurrency(CURRENCY.GBP).build(),
// responseObserver);
// service.withdraw(WithdrawRequest.newBuilder().setAmount("100").setUserID(1L).setCurrency(CURRENCY.GBP).build(),
// withdrawResponseResponseObserver);
// service.withdraw(WithdrawRequest.newBuilder().setAmount("200").setUserID(1L).setCurrency(CURRENCY.GBP).build(),
// withdrawResponseResponseObserver);
// service.balance(BalanceRequest.newBuilder().setUserID(1L).build(), balanceResponseResponseObserver);
// responseObserver.onCompleted();
//
// service.deposit(DepositRequest.newBuilder().setAmount("1000").setUserID(2L).setCurrency(CURRENCY.GBP).build(),
// responseObserver);
// service.withdraw(WithdrawRequest.newBuilder().setAmount("100").setUserID(2L).setCurrency(CURRENCY.GBP).build(),
// withdrawResponseResponseObserver);
// service.withdraw(WithdrawRequest.newBuilder().setAmount("200").setUserID(2L).setCurrency(CURRENCY.GBP).build(),
// withdrawResponseResponseObserver);
// service.balance(BalanceRequest.newBuilder().setUserID(1L).build(), balanceResponseResponseObserver);
// responseObserver.onCompleted();
//
// }
// }
