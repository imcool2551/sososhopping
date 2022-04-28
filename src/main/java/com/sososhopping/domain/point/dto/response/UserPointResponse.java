package com.sososhopping.domain.point.dto.response;

import lombok.Data;

@Data
public class UserPointResponse {

    private String name;
    private int point;

    public UserPointResponse(String name, int point) {
        this.name = name;
        this.point = point;
    }
}
