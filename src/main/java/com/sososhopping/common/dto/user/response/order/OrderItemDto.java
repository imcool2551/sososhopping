package com.sososhopping.common.dto.user.response.order;

import com.sososhopping.entity.orders.OrderItem;
import lombok.Getter;

@Getter
public class OrderItemDto {

    private String itemName;
    private String description;
    private Integer quantity;
    private Integer totalPrice;

    public OrderItemDto(OrderItem orderItem) {
        itemName = orderItem.getItem().getName();
        description = orderItem.getItem().getDescription();
        quantity = orderItem.getQuantity();
        totalPrice = orderItem.getTotalPrice();
    }
}
