package com.sososhopping.server.common.dto.user.request.order;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddCartItemDto {

    @NotNull
    private Long itemId;

    @NotNull
    private Integer quantity;
}
