package com.sososhopping.common.error;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Api409Exception extends RuntimeException{
    public Api409Exception(String message) {
        super(message);
    }
}
