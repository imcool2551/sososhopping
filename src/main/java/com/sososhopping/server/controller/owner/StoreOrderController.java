package com.sososhopping.server.controller.owner;

import com.sososhopping.server.common.dto.ApiListResponse;
import com.sososhopping.server.common.dto.user.response.order.OrderDetailDto;
import com.sososhopping.server.common.error.Api400Exception;
import com.sososhopping.server.entity.orders.Order;
import com.sososhopping.server.entity.orders.OrderStatus;
import com.sososhopping.server.service.owner.StoreOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.sososhopping.server.entity.orders.OrderStatus.*;
import static org.springframework.format.annotation.DateTimeFormat.*;

@RestController
@RequiredArgsConstructor
public class StoreOrderController {

    private final StoreOrderService storeOrderService;

    @GetMapping("/api/v1/owner/store/store/{storeId}/orders")
    public ApiListResponse<OrderDetailDto> getOrders(
            Authentication authentication,
            @PathVariable Long storeId,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate at
    ) {
        Long ownerId = Long.parseLong(authentication.getName());

        if (status == PENDING) {
            List<Order> pendingOrders = storeOrderService.getPendingOrders(ownerId, storeId);

            List<OrderDetailDto> dtos = pendingOrders.stream()
                    .map(pendingOrder -> new OrderDetailDto(pendingOrder))
                    .collect(Collectors.toList());

            return new ApiListResponse<>(dtos);
        } else if (at != null) {
            List<Order> pendingOrders = storeOrderService.getOrdersByDate(ownerId, storeId, at);

            List<OrderDetailDto> dtos = pendingOrders.stream()
                    .map(pendingOrder -> new OrderDetailDto(pendingOrder))
                    .collect(Collectors.toList());

            return new ApiListResponse<>(dtos);
        } else {
            throw new Api400Exception("알 수 없는 요청입니다");
        }
    }
}
