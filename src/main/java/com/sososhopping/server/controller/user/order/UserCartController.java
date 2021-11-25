package com.sososhopping.server.controller.user.order;

import com.sososhopping.server.common.dto.ApiResponse;
import com.sososhopping.server.common.dto.user.request.order.AddCartItemDto;
import com.sososhopping.server.common.dto.user.request.order.UpdateCartDto;
import com.sososhopping.server.common.dto.user.response.order.UserCartDto;
import com.sososhopping.server.service.user.order.UserCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class UserCartController {

    private final UserCartService userCartService;

    @PostMapping("/api/v1/users/my/cart")
    public ResponseEntity addCartItem(
            Authentication authentication,
            @RequestBody @Valid AddCartItemDto dto
    ) {
        Long userId = Long.parseLong(authentication.getName());

        userCartService.addCartItem(userId, dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }

    @GetMapping("/api/v1/users/my/cart")
    public ApiResponse<UserCartDto> getMyCart(
            Authentication authentication
    ) {
        Long userId = Long.parseLong(authentication.getName());

        List<UserCartDto> userCartDtos = new ArrayList<>();
        userCartService.getMyCart(userId)
                .stream()
                .collect(groupingBy(c -> c.getItem().getStore()))
                .forEach((k, v) -> {
                    userCartDtos.add(new UserCartDto(k, v));
                });

        return new ApiResponse<>(userCartDtos);
    }

    @PatchMapping("/api/v1/users/my/cart")
    public void updateMyCart(
            Authentication authentication,
            @RequestBody @Valid UpdateCartDto dto
    ) {
        Long userId = Long.parseLong(authentication.getName());

        userCartService.updateCart(userId, dto);
    }

    @DeleteMapping("/api/v1/users/my/cart")
    public void deleteCartItem(
            Authentication authentication,
            @RequestParam Long itemId
    ) {
        Long userId = Long.parseLong(authentication.getName());

        userCartService.deleteCartItem(userId, itemId);
    }
}
