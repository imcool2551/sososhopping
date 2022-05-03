package com.sososhopping.domain.orders.controller.owner;

import com.sososhopping.common.dto.ApiResponse;
import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.domain.orders.dto.owner.request.OrderSearchType;
import com.sososhopping.domain.orders.service.owner.OwnerOrderService;
import com.sososhopping.entity.orders.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.sososhopping.domain.orders.dto.owner.request.OrderSearchType.PENDING;
import static com.sososhopping.domain.orders.dto.owner.request.OrderSearchType.*;
import static com.sososhopping.entity.orders.OrderStatus.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OwnerOrderController {

    private final OwnerOrderService ownerOrderService;

    @GetMapping("/owner/my/store/{storeId}/orders")
    public ApiResponse findOrders(Authentication authentication,
                                  @PathVariable Long storeId,
                                  @RequestParam OrderSearchType type,
                                  @RequestParam(defaultValue = "#{T(java.time.LocalDate).now()}") LocalDate date) {


        Long ownerId = Long.parseLong(authentication.getName());

        if (type == PENDING) {
            return new ApiResponse(ownerOrderService.findPendingOrders(ownerId, storeId));
        } else if (type == PICKUP) {
            return new ApiResponse(ownerOrderService.findPickupOrders(ownerId, storeId, date));
        } else if (type == DELIVERY) {
            return new ApiResponse(ownerOrderService.findDeliveryOrders(ownerId, storeId, date));
        } else if (type == PROCESSED) {
            return new ApiResponse(ownerOrderService.findProcessedOrders(ownerId, storeId, date));
        }

        throw new UnsupportedOperationException();
    }

    @PostMapping("/owner/my/store/{storeId}/orders/{orderId}")
    public void updateOrderStatus(Authentication authentication,
                                  @PathVariable Long storeId,
                                  @PathVariable Long orderId,
                                  @RequestParam OrderStatus status) {

        if (status != APPROVE && status != READY && status != REJECT ) {
            throw new BadRequestException("invalid update status");
        }

        Long ownerId = Long.parseLong(authentication.getName());
        ownerOrderService.updateOrderStatus(storeId, ownerId, orderId, status);
    }
}
