package com.bp.wallet.server.validation;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bp.wallet.proto.StatusMessage;
import com.bp.wallet.server.exception.BPValidationException;

import io.grpc.Status;

@Component
public class BPAmountValidator implements BPBaseValidator<BigDecimal> {

    public void validate(final String amount) {
        if (StringUtils.isEmpty(amount)) {
            throw new BPValidationException(Status.FAILED_PRECONDITION, StatusMessage.INVALID_ARGUMENTS);

        }
        checkAmountFormat(amount);
        checkAmountGreaterThanZero(new BigDecimal(amount));
    }

    public void checkAmountFormat(final String amount) {
        try {
            new BigDecimal(amount);
        } catch (NumberFormatException numberFormatException) {
            throw new BPValidationException(Status.FAILED_PRECONDITION, StatusMessage.INVALID_ARGUMENTS);
        }
    }

    public void checkAmountGreaterThanZero(final BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 1) {
            throw new BPValidationException(Status.FAILED_PRECONDITION,
                    StatusMessage.AMOUNT_SHOULD_BE_GREATER_THAN_ZERO);
        }
    }

    public void checkAmountLessThanBalance(final BigDecimal currentBalance, final BigDecimal balanceToWithdraw) {
        if (currentBalance.compareTo(balanceToWithdraw) < 0) {
            throw new BPValidationException(Status.FAILED_PRECONDITION, StatusMessage.INSUFFICIENT_BALANCE);
        }
    }

}
