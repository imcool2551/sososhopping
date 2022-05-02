package com.sososhopping.domain.orders.controller.user;

import com.sososhopping.common.dto.ApiResponse;
import com.sososhopping.domain.orders.dto.user.request.UpdateCartDto;
import com.sososhopping.domain.orders.dto.user.request.AddCartItemDto;
import com.sososhopping.domain.orders.service.user.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/my/cart")
    public ApiResponse addCartItem(Authentication authentication,
                                   @RequestBody @Valid AddCartItemDto dto) {

        Long userId = Long.parseLong(authentication.getName());
        Long cartId = cartService.addCartItem(userId, dto);
        return new ApiResponse(cartId);
    }

    @PatchMapping("/users/my/cart")
    public void updateCartItem(Authentication authentication,
                               @RequestBody @Valid UpdateCartDto dto) {

        Long userId = Long.parseLong(authentication.getName());
        cartService.updateCartItem(userId, dto);
    }

    @DeleteMapping("/users/my/cart")
    public void deleteCartItem(Authentication authentication,
                               @RequestParam Long itemId) {

        Long userId = Long.parseLong(authentication.getName());
        cartService.deleteCartItem(userId, itemId);
    }
}
