package com.sososhopping.common.error;

public class Api400Exception extends RuntimeException{
    public Api400Exception(String message) {
        super(message);
    }
}
