package com.sososhopping.common.error;

public class Api500Exception extends RuntimeException{
    public Api500Exception(String message) {
        super(message);
    }
}
