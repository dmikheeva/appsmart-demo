package com.appsmart.demo.exception;

public class NoCustomerFoundException extends RuntimeException {
    public NoCustomerFoundException(Long customerId) {
        super("No customer found by id: " + customerId);
    }
}