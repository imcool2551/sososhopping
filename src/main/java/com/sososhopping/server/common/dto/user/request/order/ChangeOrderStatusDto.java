package com.sososhopping.server.common.dto.user.request.order;

import com.sososhopping.server.entity.orders.OrderStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangeOrderStatusDto {

    @NotNull
    private OrderStatus action;
}
