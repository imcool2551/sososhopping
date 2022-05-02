package com.sososhopping.domain.orders.controller.user;

import com.sososhopping.domain.orders.dto.user.response.OrderResponse;
import com.sososhopping.common.dto.OffsetBasedPageRequest;
import com.sososhopping.domain.orders.dto.user.request.CreateOrderDto;
import com.sososhopping.domain.orders.dto.user.response.OrderDetailResponse;
import com.sososhopping.common.dto.ApiResponse;
import com.sososhopping.domain.orders.service.user.CreateOrderService;
import com.sososhopping.domain.orders.service.user.OrderQueryService;
import com.sososhopping.entity.orders.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserOrderController {

    private final CreateOrderService createOrderService;
    private final OrderQueryService orderQueryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/my/orders")
    public ApiResponse createOrder(Authentication authentication,
                                   @RequestBody @Valid CreateOrderDto dto) {

        Long userId = Long.parseLong(authentication.getName());
        Long orderId = createOrderService.createOrder(userId, dto);
        return new ApiResponse(orderId);
    }

    @GetMapping("/users/my/orders/{orderId}")
    public OrderDetailResponse findOrder(Authentication authentication, @PathVariable Long orderId) {
        Long userId = Long.parseLong(authentication.getName());
        return orderQueryService.findOrder(userId, orderId);
    }

    @GetMapping("/users/my/orders")
    public Slice<OrderResponse> findOrders(Authentication authentication,
                                           @RequestParam(defaultValue = "0") int offset,
                                           @RequestParam(defaultValue = "5") int limit,
                                           @RequestParam List<OrderStatus> statuses) {

        Long userId = Long.parseLong(authentication.getName());
        return orderQueryService.findOrders(userId, statuses, new OffsetBasedPageRequest(offset, limit));
    }
}
