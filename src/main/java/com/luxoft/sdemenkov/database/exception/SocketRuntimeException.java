package com.luxoft.sdemenkov.database.exception;

public class SocketRuntimeException extends RuntimeException {
    public SocketRuntimeException(Throwable e) {
        super(e);
    }

    public SocketRuntimeException() {
    }

    public SocketRuntimeException(String s) {
        super(s);
    }
}
