package com.trikynguci.springbootvinylecommercebackend.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
