package com.sososhopping.a.dto.user.request.order;

import com.sososhopping.entity.orders.OrderStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangeOrderStatusDto {

    @NotNull
    private OrderStatus action;
}
