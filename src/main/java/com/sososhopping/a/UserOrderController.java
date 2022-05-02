package com.sososhopping.a;

import com.sososhopping.a.user.request.order.ChangeOrderStatusDto;
import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.common.error.Api401Exception;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.entity.orders.OrderStatus;
import com.sososhopping.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import static com.sososhopping.entity.orders.OrderStatus.CANCEL;
import static com.sososhopping.entity.orders.OrderStatus.DONE;

//@RestController
@RequiredArgsConstructor
public class UserOrderController {

    private final UserOrderService userOrderService;
    private final UserAuthRepository userRepository;

    @PostMapping("/api/v1/users/orders/{orderId}")
    public void changeOrderStatus(
            Authentication authentication,
            @PathVariable Long orderId,
            @RequestBody @Valid ChangeOrderStatusDto dto
    ) {
        Long userId = Long.parseLong(authentication.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid Token"));

        OrderStatus action = dto.getAction();

        if (action != CANCEL && action != DONE) {
            throw new Api400Exception("알 수 없는 요청입니다");
        }

        if (action == CANCEL) {
            userOrderService.cancelOrder(user, orderId);
        } else if (action == DONE) {
            userOrderService.confirmOrder(user, orderId);
        }
    }


}
