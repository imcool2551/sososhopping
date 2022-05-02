package com.sososhopping.a.user.response.order;

import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.orders.OrderStatus;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class OrderListDto {

    private final Long orderId;
    private final String description;
    private final String imgUrl;
    private final String orderType;
    private final Integer finalPrice;
    private final OrderStatus orderStatus;
    private final String createdAt;

    public OrderListDto(Order order) {
        orderId = order.getId();
        description =
                order.getOrderItems().get(0).getItem().getName() + " 외 "
                + (order.getOrderItems().size() - 1) + "건";
        imgUrl = order.getStore().getImgUrl();
        orderType = order.getOrderType().getKrOrderType();
        finalPrice = order.getFinalPrice();
        orderStatus = order.getOrderStatus();
        createdAt = order.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
}
