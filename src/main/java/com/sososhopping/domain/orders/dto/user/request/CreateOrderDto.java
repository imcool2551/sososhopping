package com.sososhopping.domain.orders.dto.user.request;

import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.orders.OrderType;
import com.sososhopping.entity.orders.PaymentType;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sososhopping.entity.orders.OrderStatus.PENDING;
import static java.util.stream.Collectors.toMap;

@Data
public class CreateOrderDto {

    @NotNull(message = "점포 id 필수")
    private Long storeId;

    @Valid
    @Size(min = 1)
    private List<CreateOrderItemDto> orderItems = new ArrayList<>();

    @NotNull(message = "주문 타입 필수")
    private OrderType orderType;

    @NotNull(message = "결제 타입 필수")
    private PaymentType paymentType;

    @Min(value = 100, message = "100원 부터 사용 가능")
    private Integer usedPoint;

    private Long couponId;

    @NotNull(message = "최종 가격 필수")
    private Integer finalPrice;

    @NotNull(message = "주문자 이름 필수")
    @NotBlank(message = "주문자 이름 필수")
    private String ordererName;

    @NotNull(message = "주문자 번호 필수")
    @NotBlank(message = "주문자 번호 필수")
    private String ordererPhone;

    private LocalDateTime visitDate;

    private String deliveryStreetAddress;

    private String deliveryDetailedAddress;

    public Order toOrder(User user, Store store, Coupon coupon,
                         int finalPrice, int orderPrice, int deliveryCharge) {

        return Order.builder()
                .user(user)
                .ordererName(ordererName)
                .ordererPhone(ordererPhone)
                .orderType(orderType)
                .visitDate(visitDate)
                .store(store)
                .deliveryCharge(deliveryCharge)
                .deliveryStreetAddress(deliveryStreetAddress)
                .deliveryDetailedAddress(deliveryDetailedAddress)
                .paymentType(paymentType)
                .orderPrice(orderPrice)
                .usedPoint(usedPoint)
                .coupon(coupon)
                .finalPrice(finalPrice)
                .orderStatus(PENDING)
                .build();
    }

    public Map<Long, Integer> itemIdToQuantity() {
        return orderItems.stream()
                .collect(toMap(CreateOrderItemDto::getItemId, CreateOrderItemDto::getQuantity));
    }

    public List<Long> itemIds() {
        return orderItems.stream()
                .map(CreateOrderItemDto::getItemId)
                .collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    public static class CreateOrderItemDto {

        @NotNull(message = "물품 id 필수")
        private Long itemId;

        @NotNull(message = "물품 수량 필수")
        @Min(1)
        private Integer quantity;
    }
}








