package com.sososhopping.a.dto.user.request.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sososhopping.entity.orders.OrderType;
import com.sososhopping.entity.orders.PaymentType;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class OrderCreateDto {

    @NotNull
    private Long storeId;

    @Valid
    @Size(min = 1)
    private List<OrderItemDto> orderItems = new ArrayList<>();

    @NotNull
    private OrderType orderType;

    @NotNull
    private PaymentType paymentType;

    @NotNull
    private Integer usedPoint;

    private Long couponId;

    @NotNull
    private Integer finalPrice;

    @NotNull
    private String ordererName;

    @NotNull
    private String ordererPhone;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @FutureOrPresent
    private LocalDateTime visitDate;

    private String deliveryStreetAddress;

    private String deliveryDetailedAddress;

    @Data
    public static class OrderItemDto {

        @NotNull
        private Long itemId;

        @Min(1)
        private Integer quantity;
    }
}
