package com.sososhopping.a;

import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.entity.orders.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.sososhopping.entity.orders.OrderStatus.*;

//@RestController
@RequiredArgsConstructor
public class OwnerOrderController {

    private final OwnerOrderService ownerOrderService;


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
