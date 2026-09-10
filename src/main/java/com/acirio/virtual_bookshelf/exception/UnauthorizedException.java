package com.acirio.virtual_bookshelf.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException (String message) {
        super(message);
    }
}
