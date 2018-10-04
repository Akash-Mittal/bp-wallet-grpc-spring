package com.bp.wallet.server.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bp.wallet.proto.StatusMessage;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

public class BPBaseException extends StatusRuntimeException {
    private static final long serialVersionUID = 4267114442294762693L;
    private static final Logger logger = LoggerFactory.getLogger(BPBaseException.class);

    private StatusMessage errorStatus;

    public BPBaseException(Status status, Metadata trailers) {
        super(status, trailers);
    }

    public BPBaseException(Status status) {
        super(status);
    }

    public BPBaseException(Status status, StatusMessage errorStatus) {
        super(status);
        this.errorStatus = errorStatus;
    }

    public StatusMessage getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(StatusMessage errorStatus) {
        this.errorStatus = errorStatus;
    }
}
