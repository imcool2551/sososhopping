package com.sososhopping.server.common.dto.user.request.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class OrderItemDto {

    @NotNull
    private Long itemId;

    @Min(1)
    private Integer quantity;
}
