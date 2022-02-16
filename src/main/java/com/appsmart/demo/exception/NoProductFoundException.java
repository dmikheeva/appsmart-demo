package com.appsmart.demo.exception;

public class NoProductFoundException extends RuntimeException {
    public NoProductFoundException(Long productId) {
        super("No product found by id: " + productId);
    }
}