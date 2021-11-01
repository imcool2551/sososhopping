package com.sososhopping.server.common.error;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Api401Exception extends RuntimeException{
    public Api401Exception(String message) {
        super(message);
    }
}
