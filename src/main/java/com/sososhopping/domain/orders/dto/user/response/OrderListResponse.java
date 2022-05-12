package com.sososhopping.domain.orders.dto.user.response;

import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.orders.OrderStatus;
import com.sososhopping.entity.store.Item;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderListResponse {

    private Long orderId;
    private String firstItemName;
    private int totalItemSize;
    private String imgUrl;
    private String orderType;
    private int finalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;

    public OrderListResponse(Order order, Item firstItem, int totalItemSize) {
        this.orderId = order.getId();
        this.firstItemName = firstItem.getName();
        this.totalItemSize = totalItemSize;
        this.imgUrl = order.getStore()
                .getImgUrl();
        this.orderType = order.getOrderType()
                .getKrOrderType();
        this.finalPrice = order.getFinalPrice();
        this.orderStatus = order.getOrderStatus();
        this.createdAt = order.getCreatedAt();
    }
}
