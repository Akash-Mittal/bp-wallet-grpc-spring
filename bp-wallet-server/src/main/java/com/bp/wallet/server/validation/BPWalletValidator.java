package com.bp.wallet.server.validation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.bp.wallet.proto.StatusMessage;
import com.bp.wallet.server.enity.Wallet;
import com.bp.wallet.server.exception.BPValidationException;

import io.grpc.Status;

@Component
public class BPWalletValidator implements BPBaseValidator<Optional<List<Wallet>>> {

    public void validateWallet(Optional<Wallet> t) {
        if (!t.isPresent()) {
            throw new BPValidationException(Status.INTERNAL, StatusMessage.USER_DOES_NOT_EXIST);
        }

    }

    public void validate(Optional<List<Wallet>> t) {
        if (!t.isPresent()) {
            throw new BPValidationException(Status.INTERNAL, StatusMessage.USER_DOES_NOT_EXIST);

        }
        if (CollectionUtils.isEmpty(t.get())) {
            throw new BPValidationException(Status.INTERNAL, StatusMessage.USER_DOES_NOT_EXIST);

        }
    }
}
