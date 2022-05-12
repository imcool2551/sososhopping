package com.sososhopping.common.exception;

public class Api500Exception extends RuntimeException{
    public Api500Exception(String message) {
        super(message);
    }
}
