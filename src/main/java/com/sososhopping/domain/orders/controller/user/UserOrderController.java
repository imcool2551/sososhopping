package com.sososhopping.domain.orders.controller.user;

import com.sososhopping.a.user.request.order.CreateOrderDto;
import com.sososhopping.common.dto.ApiResponse;
import com.sososhopping.domain.orders.service.user.CreateOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserOrderController {

    private final CreateOrderService createOrderService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/my/orders")
    public ApiResponse createOrder(Authentication authentication,
                                   @RequestBody @Valid CreateOrderDto dto) {

        Long userId = Long.parseLong(authentication.getName());
        Long orderId = createOrderService.createOrder(userId, dto);
        return new ApiResponse(orderId);
    }
}
