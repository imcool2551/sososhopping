package com.sososhopping.domain.orders.dto.owner.response;

import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.orders.OrderItem;
import com.sososhopping.entity.orders.OrderStatus;
import com.sososhopping.entity.orders.PaymentType;
import com.sososhopping.entity.owner.Owner;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class OrderListResponse {

    @Data
    static class OrderItemResponse {

        private String itemName;

        private String description;
        private int quantity;
        private int totalPrice;
        public OrderItemResponse(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            description = orderItem.getItem().getDescription();
            quantity = orderItem.getQuantity();
            totalPrice = orderItem.getTotalPrice();
        }

    }
    private Long orderId;

    private Long userId;
    private Long ownerId;
    private Long storeId;
    private OrderStatus orderStatus;
    private PaymentType paymentType;
    private String phone;
    private List<OrderItemResponse> orderItems;
    private String ordererName;
    private String ordererPhone;
    private String orderType;
    private LocalDateTime visitDate;
    private int deliveryCharge;
    private String deliveryStreetAddress;
    private String deliveryDetailedAddress;
    private int orderPrice;
    private Integer usedPoint;
    private int couponDiscountPrice;
    private int finalPrice;
    private LocalDateTime createdAt;

    public OrderListResponse(User user, Owner owner, Order order, Store store) {
        orderId = order.getId();
        userId = user.getId();
        ownerId = owner.getId();
        storeId = store.getId();
        orderStatus = order.getOrderStatus();
        paymentType = order.getPaymentType();
        phone = store.getPhone();
        orderItems = order.getOrderItems()
                .stream()
                .map(OrderItemResponse::new)
                .collect(Collectors.toList());
        ordererName = order.getOrdererName();
        ordererPhone = order.getOrdererPhone();
        orderType = order.getOrderType()
                .getKrOrderType();
        visitDate = order.getVisitDate();
        deliveryCharge = order.getDeliveryCharge();
        deliveryStreetAddress = order.getDeliveryStreetAddress();
        deliveryDetailedAddress = order.getDeliveryDetailedAddress();
        orderPrice = order.getOrderPrice();
        usedPoint = order.getUsedPoint();
        couponDiscountPrice = Optional.ofNullable(order.getCoupon())
                .map(coupon -> coupon.calculateDiscountPrice(orderPrice))
                .orElse(0);
        finalPrice = order.getFinalPrice();
        createdAt = order.getCreatedAt();
    }
}
