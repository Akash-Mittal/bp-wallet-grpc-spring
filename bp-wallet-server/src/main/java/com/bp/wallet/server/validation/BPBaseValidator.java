package com.bp.wallet.server.validation;

import java.util.Objects;

import com.bp.wallet.proto.StatusMessage;
import com.bp.wallet.server.exception.BPValidationException;

import io.grpc.Status;

public interface BPBaseValidator<T> {

    default void validate(T t, String params) {
        if (Objects.isNull(t)) {
            throw new BPValidationException(Status.FAILED_PRECONDITION, StatusMessage.INVALID_ARGUMENTS);
        }
    }

}
