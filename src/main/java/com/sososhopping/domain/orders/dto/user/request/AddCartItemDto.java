package com.sososhopping.domain.orders.dto.user.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AddCartItemDto {

    @NotNull(message = "item id 필수")
    private Long itemId;

    @NotNull(message = "수량 필수")
    @Min(value = 1, message = "최소 수량 1")
    private Integer quantity;
}
