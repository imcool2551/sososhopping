package com.sososhopping.common.error;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Api403Exception extends RuntimeException{
    public Api403Exception(String message) {
        super(message);
    }
}
