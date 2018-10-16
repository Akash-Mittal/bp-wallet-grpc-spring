package com.bp.wallet.server.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bp.wallet.proto.BaseRequest;
import com.bp.wallet.proto.BaseResponse;
import com.bp.wallet.proto.STATUS;
import com.bp.wallet.proto.WalletServiceGrpc;
import com.bp.wallet.server.dto.BalanceResponseDTO;
import com.bp.wallet.server.enity.Wallet;
import com.bp.wallet.server.exception.BPValidationException;
import com.bp.wallet.server.repository.WalletRepository;
import com.bp.wallet.server.validation.BPAmountValidator;
import com.bp.wallet.server.validation.BPCurrencyValidator;
import com.bp.wallet.server.validation.BPWalletValidator;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;

@GrpcService(WalletServiceGrpc.class)
public class WalletServerService extends WalletServiceGrpc.WalletServiceImplBase {

	private static final Logger logger = LoggerFactory.getLogger(WalletServerService.class);
	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private BPAmountValidator bpAmountValidator;

	@Autowired
	private BPCurrencyValidator bpCurrencyValidator;
	@Autowired
	private BPWalletValidator bpWalletValidator;

	@Autowired
	private BalanceResponseDTO balanceResponseDTO;

	@Override
	@Transactional
	public void deposit(final BaseRequest request, final StreamObserver<BaseResponse> responseObserver) {
		try {

			validateRequest(request);
			final BigDecimal balanceToADD = get(request.getAmount());
			logger.info("Request Recieved for UserID:{} For Amount:{}{} ", request.getUserID(), request.getAmount(),
					request.getCurrency());
			Optional<Wallet> wallet = getUserWallet(request);
			updateWallet(request, balanceToADD, wallet);
			successResponse(responseObserver);
			logger.info("Wallet Updated SuccessFully");
		} catch (BPValidationException e) {
			logger.error(e.getErrorStatus().name());
			responseObserver
					.onError(new StatusRuntimeException(e.getStatus().withDescription(e.getErrorStatus().name())));
		} catch (Exception e) {
			logger.error("------------>", e);
			responseObserver.onError(new StatusRuntimeException(Status.UNKNOWN.withDescription(e.getMessage())));
		} finally {
			walletRepository.flush();
		}
	}

	@Override
	@Transactional

	public void withdraw(final BaseRequest request, final StreamObserver<BaseResponse> responseObserver) {

		logger.info("Request Recieved for UserID:{} For Amount:{}{} ", request.getUserID(), request.getAmount(),
				request.getCurrency());
		try {
			final BigDecimal balanceToWithdraw = get(request.getAmount());
			validateRequest(request);
			Optional<Wallet> wallet = getUserWallet(request);
			validateWithDrawRequest(balanceToWithdraw, wallet);
			updateWallet(request, wallet.get().getBalance().subtract(balanceToWithdraw), wallet);
			successResponse(responseObserver);
		} catch (BPValidationException e) {
			logger.error(e.getErrorStatus().name());
			responseObserver
					.onError(new StatusRuntimeException(e.getStatus().withDescription(e.getErrorStatus().name())));
		} catch (Exception e) {
			logger.error("------------>", e);
			responseObserver.onError(new StatusRuntimeException(Status.UNKNOWN.withDescription(e.getMessage())));
		} finally {
			walletRepository.flush();
		}
	}

	@Override
	@Transactional

	public void balance(final BaseRequest request, final StreamObserver<BaseResponse> responseObserver) {
		logger.info("Request Recieved for UserID:{}", request.getUserID());
		try {
			Optional<List<Wallet>> userWallets = walletRepository.findByWalletPK_UserID(request.getUserID());
			bpWalletValidator.validate(userWallets);
			String balance = balanceResponseDTO.getBalanceAsString(userWallets);
			logger.info(balance);
			responseObserver.onNext(BaseResponse.newBuilder().setStatusMessage(balance)
					.setStatus((STATUS.TRANSACTION_SUCCESS)).build());
			responseObserver.onCompleted();
		} catch (BPValidationException e) {
			logger.error(e.getErrorStatus().name());
			responseObserver
					.onError(new StatusRuntimeException(e.getStatus().withDescription(e.getErrorStatus().name())));
		} catch (Exception e) {
			logger.error("------------>", e);
			responseObserver.onError(new StatusRuntimeException(Status.UNKNOWN.withDescription(e.getMessage())));
		} finally {

		}

	}

	private BigDecimal get(final String val) {
		return new BigDecimal(val);
	}

	private void validateRequest(final BaseRequest request) {
		bpAmountValidator.validate(request.getAmount());
		bpCurrencyValidator.checkCurrency(request.getCurrency());
	}

	private void successResponse(final StreamObserver<BaseResponse> responseObserver) {
		responseObserver.onNext(BaseResponse.newBuilder().setStatus(STATUS.TRANSACTION_SUCCESS).build());
		responseObserver.onCompleted();

	}

	private Optional<Wallet> getUserWallet(final BaseRequest request) {
		Optional<Wallet> wallet = walletRepository.getUserWalletsByCurrencyAndUserID(request.getUserID(),
				request.getCurrency());
		return wallet;
	}

	private void updateWallet(final BaseRequest request, final BigDecimal balanceToADD, final Optional<Wallet> wallet) {
		bpWalletValidator.validateWallet(wallet);
		walletRepository.updateBalance(wallet.get().getBalance().add(balanceToADD), request.getUserID(),
				request.getCurrency());
	}

	private void validateWithDrawRequest(final BigDecimal balanceToWithdraw, Optional<Wallet> wallet) {
		bpWalletValidator.validateWallet(wallet);
		bpAmountValidator.checkAmountLessThanBalance(wallet.get().getBalance(), balanceToWithdraw);
	}

}
