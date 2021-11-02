package com.sososhopping.server.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiResponse<T> {
    private List<T> results = new ArrayList<>();

    public ApiResponse(List<T> results) {
        this.results = results;
    }

    public ApiResponse(T result) {
        results.add(result);
    }
}
