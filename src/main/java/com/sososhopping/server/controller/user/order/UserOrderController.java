package com.sososhopping.server.controller.user.order;

import com.sososhopping.server.common.dto.user.request.order.OrderCreateDto;
import com.sososhopping.server.common.dto.user.request.order.OrderItemDto;
import com.sososhopping.server.common.error.Api401Exception;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.repository.member.UserRepository;
import com.sososhopping.server.service.user.order.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserOrderController {

    private final UserOrderService userOrderService;
    private final UserRepository userRepository;

    @PostMapping("/api/v1/orders")
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
}
