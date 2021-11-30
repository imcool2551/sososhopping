package com.sososhopping.server.controller.owner;

import com.sososhopping.server.common.dto.ApiListResponse;
import com.sososhopping.server.common.dto.owner.request.OrderSearchType;
import com.sososhopping.server.common.dto.user.request.order.ChangeOrderStatusDto;
import com.sososhopping.server.common.dto.user.response.order.OrderDetailDto;
import com.sososhopping.server.common.error.Api400Exception;
import com.sososhopping.server.entity.orders.Order;
import com.sososhopping.server.entity.orders.OrderStatus;
import com.sososhopping.server.service.owner.StoreOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.sososhopping.server.common.dto.owner.request.OrderSearchType.*;
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
            @RequestParam(required = false) OrderSearchType type,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate at
    ) {
        Long ownerId = Long.parseLong(authentication.getName());
        List<Order> orders = null;
        List<OrderDetailDto> dtos = null;

        if (type == OrderSearchType.PENDING) {
            orders = storeOrderService.findPendingOrders(ownerId, storeId);

            dtos = orders.stream()
                    .map(pendingOrder -> new OrderDetailDto(pendingOrder))
                    .collect(Collectors.toList());

        } else if (type == PICKUP) {
            orders = storeOrderService.findPickupOrders(ownerId, storeId);

            dtos = orders.stream()
                    .map(pickupOrder -> new OrderDetailDto(pickupOrder))
                    .collect(Collectors.toList());

        } else if (type == DELIVERY) {
            orders = storeOrderService.findDeliveryOrders(ownerId, storeId);

            dtos = orders.stream()
                    .map(deliveryOrder -> new OrderDetailDto(deliveryOrder))
                    .collect(Collectors.toList());
        } else if (at != null) {
            orders = storeOrderService.findDoneOrdersByDate(ownerId, storeId, at);

            dtos = orders.stream()
                    .map(doneOrder -> new OrderDetailDto(doneOrder))
                    .collect(Collectors.toList());
        } else {
            throw new Api400Exception("알 수 없는 요청입니다");
        }

        return new ApiListResponse<>(dtos);
    }

    @PostMapping("/api/v1/owner/store/{storeId}/orders/{orderId}")
    public void handleOrder(
            Authentication authentication,
            @PathVariable Long storeId,
            @PathVariable Long orderId,
            @RequestBody @Valid ChangeOrderStatusDto dto
    ) {
        Long ownerId = Long.parseLong(authentication.getName());

        OrderStatus action = dto.getAction();

        if (action != APPROVE || action != REJECT || action != READY) {
            throw new Api400Exception("알 수 없는 요청입니다");
        }

        storeOrderService.handleOrder(ownerId, storeId, orderId, action);
    }
}
