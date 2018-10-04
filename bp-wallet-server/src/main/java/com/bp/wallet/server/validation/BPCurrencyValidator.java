package com.bp.wallet.server.validation;

import org.springframework.stereotype.Component;

import com.bp.wallet.proto.CURRENCY;
import com.bp.wallet.proto.StatusMessage;
import com.bp.wallet.server.exception.BPValidationException;

import io.grpc.Status;

@Component
public class BPCurrencyValidator implements BPBaseValidator<CURRENCY> {

    public void checkCurrency(final CURRENCY currency) {
        if (currency.equals(CURRENCY.UNRECOGNIZED)) {
            throw new BPValidationException(Status.FAILED_PRECONDITION, StatusMessage.INVALID_CURRENCY);
        }
    }

}
