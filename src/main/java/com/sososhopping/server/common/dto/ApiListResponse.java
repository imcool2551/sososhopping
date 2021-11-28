package com.sososhopping.server.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiListResponse<T> {
    private List<T> results;

    public ApiListResponse(List<T> results) {
        this.results = results;
    }
}
