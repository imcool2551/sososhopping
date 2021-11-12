package com.sososhopping.server.common.dto.user.response.order;

import com.sososhopping.server.entity.orders.Order;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class OrderListDto {

    private final Long orderId;
    private final String storeName;
    private final String imgUrl;
    private final String orderType;
    private final Integer finalPrice;
    private final String createdAt;

    public OrderListDto(Order order) {
        orderId = order.getId();
        storeName = order.getStoreName();
        imgUrl = order.getStore().getImgUrl();
        orderType = order.getOrderType().getKrOrderType();
        finalPrice = order.getFinalPrice();
        createdAt = order.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
}
