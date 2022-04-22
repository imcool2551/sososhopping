package com.sososhopping.common.error;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Api400Exception extends RuntimeException{
    public Api400Exception(String message) {
        super(message);
    }
}
