package com.bp.wallet.server.exception;

import com.bp.wallet.proto.StatusMessage;

import io.grpc.Metadata;
import io.grpc.Status;

public class BPServiceException extends BPBaseException {

    private static final long serialVersionUID = 4890201354059182901L;

    public BPServiceException(Status status, Metadata trailers) {
        super(status, trailers);
        // TODO Auto-generated constructor stub
    }

    public BPServiceException(Status status, StatusMessage errorStatus) {
        super(status, errorStatus);
        // TODO Auto-generated constructor stub
    }

    public BPServiceException(Status status) {
        super(status);
        // TODO Auto-generated constructor stub
    }
}
