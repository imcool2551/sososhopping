package com.sososhopping.server.controller.user.order;

import com.sososhopping.server.common.dto.ApiResponse;
import com.sososhopping.server.common.dto.user.request.order.OrderCreateDto;
import com.sososhopping.server.common.dto.user.request.order.OrderItemDto;
import com.sososhopping.server.common.dto.user.response.order.OrderDetailDto;
import com.sososhopping.server.common.dto.user.response.order.OrderListDto;
import com.sososhopping.server.common.error.Api401Exception;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.orders.Order;
import com.sososhopping.server.entity.orders.OrderStatus;
import com.sososhopping.server.entity.orders.OrderType;
import com.sososhopping.server.repository.member.UserRepository;
import com.sososhopping.server.service.user.order.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserOrderController {

    private final UserOrderService userOrderService;
    private final UserRepository userRepository;

    @PostMapping("/api/v1/users/orders")
    public ResponseEntity makeOrder(
            Authentication authentication,
            @RequestBody @Valid OrderCreateDto orderCreateDto
    ) {

        Long userId = Long.parseLong(authentication.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid Token"));

        userOrderService.createOrder(user, orderCreateDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }

    @GetMapping("/api/v1/users/my/orders")
    public ApiResponse<OrderListDto> getMyOrders(
            Authentication authentication,
            @RequestParam OrderStatus status
    ) {
        Long userId = Long.parseLong(authentication.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid Token"));

        List<OrderListDto> dtos = userOrderService.getOrders(user, status)
                .stream()
                .map(order -> new OrderListDto((order)))
                .collect(Collectors.toList());

        return new ApiResponse<OrderListDto>(dtos);
    }

    @GetMapping("/api/v1/users/my/orders/{orderId}")
    public ApiResponse<OrderDetailDto> getOrderDetail(
            Authentication authentication,
            @PathVariable Long orderId
    ) {
        Long userId = Long.parseLong(authentication.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid Token"));

        Order order = userOrderService.getOrderDetail(user, orderId);
        OrderDetailDto dto = new OrderDetailDto(order);
        return new ApiResponse<OrderDetailDto>(dto);
    }
}
