package com.sososhopping.a;

import com.sososhopping.a.user.request.order.ChangeOrderStatusDto;
import com.sososhopping.a.user.response.order.OrderDetailDto;
import com.sososhopping.a.user.response.order.OrderListDto;
import com.sososhopping.common.dto.ApiListResponse;
import com.sososhopping.common.dto.OffsetBasedPageRequest;
import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.common.error.Api401Exception;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.orders.OrderStatus;
import com.sososhopping.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.sososhopping.entity.orders.OrderStatus.CANCEL;
import static com.sososhopping.entity.orders.OrderStatus.DONE;

//@RestController
@RequiredArgsConstructor
public class UserOrderController {

    private final UserOrderService userOrderService;
    private final UserAuthRepository userRepository;
    private final OrderRepository orderRepository;

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

    @GetMapping("/api/v1/users/my/orders")
    public ApiListResponse<OrderListDto> getOrders(
            Authentication authentication,
            @RequestParam List<OrderStatus> statuses
    ) {
        Long userId = Long.parseLong(authentication.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid Token"));

        List<OrderListDto> dtos = userOrderService.getOrders(user, statuses);

        return new ApiListResponse<OrderListDto>(dtos);
    }

    @GetMapping("/api/v1/users/my/orders/page")
    public Slice<OrderListDto> getOrdersPageable(
            Authentication authentication,
            @RequestParam List<OrderStatus> statuses,
            @RequestParam Integer offset
    ) {
        Long userId = Long.parseLong(authentication.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid Token"));

        OrderStatus[] statusArray =
                statuses.toArray(new OrderStatus[statuses.size()]);

        Pageable pageable = new OffsetBasedPageRequest(offset, 10);

        return orderRepository.findOrdersByUserAndOrderStatus(user, pageable, statusArray)
                .map(OrderListDto::new);
    }

    @GetMapping("/api/v1/users/my/orders/{orderId}")
    public OrderDetailDto getOrderDetail(
            Authentication authentication,
            @PathVariable Long orderId
    ) {
        Long userId = Long.parseLong(authentication.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid Token"));

        Order order = userOrderService.getOrderDetail(user, orderId);
        return new OrderDetailDto(order);
    }
}
