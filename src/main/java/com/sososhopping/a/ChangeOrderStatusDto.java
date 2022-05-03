package com.sososhopping.a;

import com.sososhopping.entity.orders.OrderStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangeOrderStatusDto {

    @NotNull
    private OrderStatus action;
}
