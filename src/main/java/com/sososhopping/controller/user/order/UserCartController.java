package com.sososhopping.controller.user.order;

import com.sososhopping.common.dto.ApiListResponse;
import com.sososhopping.common.dto.user.request.order.AddCartItemDto;
import com.sososhopping.common.dto.user.request.order.UpdateCartDto;
import com.sososhopping.common.dto.user.response.order.UserCartDto;
import com.sososhopping.entity.user.Cart;
import com.sososhopping.entity.store.Store;
import com.sososhopping.service.user.order.UserCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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
    public ApiListResponse<UserCartDto> getMyCart(
            Authentication authentication
    ) {
        Long userId = Long.parseLong(authentication.getName());

        Map<Store, List<Cart>> storeToCarts = userCartService.getMyCart(userId)
                .stream()
                .collect(groupingBy(c -> c.getItem().getStore()));

        List<Map.Entry<Store, List<Cart>>> entries = new LinkedList<>(storeToCarts.entrySet());
        Collections.sort(entries, Comparator.comparing(o -> o.getKey().getId()));

        LinkedHashMap<Store, List<Cart>> orderedStoreToCarts = new LinkedHashMap<>();
        for (Map.Entry<Store, List<Cart>> entry : entries) {
            orderedStoreToCarts.put(entry.getKey(), entry.getValue());
        }

        List<UserCartDto> userCartsDto = new ArrayList<>();
        orderedStoreToCarts
                .forEach((k, v) -> {
                    userCartsDto.add(new UserCartDto(k, v));
                });

        return new ApiListResponse<>(userCartsDto);
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
