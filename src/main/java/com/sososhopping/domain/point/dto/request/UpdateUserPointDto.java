package com.sososhopping.domain.point.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateUserPointDto {

    @NotNull(message = "고객 전화번호 필수")
    @NotBlank(message = "고객 전화번호 필수")
    private String userPhone;

    @NotNull(message = "적립 금액 필수")
    private Integer amount;
}
