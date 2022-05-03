package com.sososhopping.a;

import com.sososhopping.domain.auth.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;

//@RestController
@RequiredArgsConstructor
public class UserOrderController {

    private final UserOrderService userOrderService;
    private final UserAuthRepository userRepository;

//    @PostMapping("/api/v1/users/orders/{orderId}")
//    public void changeOrderStatus(
//            Authentication authentication,
//            @PathVariable Long orderId,
//            @RequestBody @Valid ChangeOrderStatusDto dto
//    ) {
//        Long userId = Long.parseLong(authentication.getName());
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new Api401Exception("Invalid Token"));
//
//        OrderStatus action = dto.getAction();
//
//        if (action != CANCEL && action != DONE) {
//            throw new Api400Exception("알 수 없는 요청입니다");
//        }
//
//        if (action == CANCEL) {
//            userOrderService.cancelOrder(user, orderId);
//        } else if (action == DONE) {
//            userOrderService.confirmOrder(user, orderId);
//        }
//    }


}
