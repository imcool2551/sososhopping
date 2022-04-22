package com.sososhopping.common.dto.user.response.order;

import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.orders.OrderStatus;
import com.sososhopping.entity.orders.PaymentType;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class OrderDetailDto {

    private Long orderId;
    private Long userId;
    private Long ownerId;
    private Long storeId;
    private OrderStatus orderStatus;
    private PaymentType paymentType;
    private String phone;
    private List<OrderItemDto> orderItems;
    private String ordererName;
    private String ordererPhone;
    private String orderType;
    private String visitDate;
    private String storeName;
    private Integer deliveryCharge;
    private String deliveryStreetAddress;
    private String deliveryDetailedAddress;
    private Integer orderPrice;
    private Integer usedPoint;
    private Integer couponDiscountPrice;
    private Integer finalPrice;
    private String createdAt;

    public OrderDetailDto(Order order) {
        orderId = order.getId();
        userId = order.getUser().getId();
        ownerId = order.getStore().getOwner().getId();
        storeId = order.getStore().getId();
        orderStatus = order.getOrderStatus();
        paymentType = order.getPaymentType();
        phone = order.getStore().getPhone();
        orderItems = order.getOrderItems().stream()
                .map(orderItem -> new OrderItemDto(orderItem))
                .collect(Collectors.toList());
        ordererName = order.getOrdererName();
        ordererPhone = order.getOrdererPhone();
        orderType = order.getOrderType().getKrOrderType();
        visitDate = Optional.ofNullable(order.getVisitDate())
                .map(visitDate -> visitDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                .orElse(null);
        storeName = order.getStoreName();
        deliveryCharge = order.getDeliveryCharge();
        deliveryStreetAddress = order.getDeliveryStreetAddress();
        deliveryDetailedAddress = order.getDeliveryDetailedAddress();
        orderPrice = order.getOrderPrice();
        usedPoint = order.getUsedPoint();
        couponDiscountPrice = Optional.ofNullable(order.getCoupon())
                .map(coupon -> coupon.getDiscountPrice(orderPrice))
                .orElse(null);
        finalPrice = order.getFinalPrice();
        createdAt = order.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
}
