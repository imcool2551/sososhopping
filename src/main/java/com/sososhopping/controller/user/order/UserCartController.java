package com.sososhopping.controller.user.order;

import com.sososhopping.common.dto.ApiListResponse;
import com.sososhopping.common.dto.user.response.order.UserCartDto;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.Cart;
import com.sososhopping.service.user.order.UserCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

//@RestController
@RequiredArgsConstructor
public class UserCartController {

    private final UserCartService userCartService;


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


    @DeleteMapping("/api/v1/users/my/cart")
    public void deleteCartItem(
            Authentication authentication,
            @RequestParam Long itemId
    ) {
        Long userId = Long.parseLong(authentication.getName());

        userCartService.deleteCartItem(userId, itemId);
    }
}
