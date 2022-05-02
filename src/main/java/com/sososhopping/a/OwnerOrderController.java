package com.sososhopping.a;

import com.sososhopping.a.owner.request.OrderSearchType;
import com.sososhopping.a.user.request.order.ChangeOrderStatusDto;
import com.sososhopping.common.dto.ApiListResponse;
import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.domain.orders.dto.user.response.OrderDetailResponse;
import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.orders.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static com.sososhopping.a.owner.request.OrderSearchType.DELIVERY;
import static com.sososhopping.a.owner.request.OrderSearchType.PICKUP;
import static com.sososhopping.entity.orders.OrderStatus.*;

@RestController
@RequiredArgsConstructor
public class OwnerOrderController {

    private final OwnerOrderService ownerOrderService;

    @GetMapping("/api/v1/owner/store/{storeId}/orders")
    public ApiListResponse<OrderDetailResponse> getOrders(
            Authentication authentication,
            @PathVariable Long storeId,
            @RequestParam(required = false) OrderSearchType type,
            @RequestParam(required = false) LocalDate at
    ) {
        Long ownerId = Long.parseLong(authentication.getName());
        List<Order> orders = null;
        List<OrderDetailResponse> dtos = null;

        if (type == OrderSearchType.PENDING) {
            orders = ownerOrderService.findPendingOrders(ownerId, storeId);



        } else if (type == PICKUP) {
            orders = ownerOrderService.findPickupOrders(ownerId, storeId);


        } else if (type == DELIVERY) {
            orders = ownerOrderService.findDeliveryOrders(ownerId, storeId);


        } else if (at != null) {
            orders = ownerOrderService.findDoneOrdersByDate(ownerId, storeId, at);
//
//            dtos = orders.stream()
//                    .map(doneOrder -> new OrderDetailResponse(doneOrder, user, order.getStore()))
//                    .collect(Collectors.toList());
        } else {
            throw new Api400Exception("알 수 없는 요청입니다");
        }

        return null;
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

        if (action != APPROVE && action != REJECT && action != READY) {
            throw new Api400Exception("알 수 없는 요청입니다");
        }

        ownerOrderService.handleOrder(ownerId, storeId, orderId, action);
    }
}
