package com.sososhopping.a;

import com.sososhopping.a.user.response.order.OrderListDto;
import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.common.error.Api401Exception;
import com.sososhopping.common.error.Api404Exception;
import com.sososhopping.domain.coupon.repository.UserCouponRepository;
import com.sososhopping.domain.point.repository.UserPointRepository;
import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.orders.OrderStatus;
import com.sososhopping.entity.point.UserPoint;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

//@Service
@RequiredArgsConstructor
public class UserOrderService {

    private final UserPointRepository userPointRepository;
    private final UserCouponRepository userCouponRepository;
    private final OrderRepository orderRepository;
    private final EntityManager em;


    @Transactional
    public List<OrderListDto> getOrders(User user, List<OrderStatus> statuses) {
        OrderStatus[] statusArray =
                statuses.toArray(new OrderStatus[statuses.size()]);
        List<Order> orders = orderRepository.findOrderListByUserAndOrderStatus(user, statusArray);

        List<OrderListDto> list = new ArrayList<>();
        orders.forEach(order -> {
            list.add(new OrderListDto(order));
        });
        return list;
    }

    @Transactional
    public Order getOrderDetail(User user, Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 주문입니다"));

        if (user != order.getUser()) {
            throw new Api401Exception("다른 고객의 주문입니다");
        }
        return order;
    }

    @Transactional
    public void cancelOrder(User user, Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 주문입니다"));

        Store store = order.getStore();

        if (order.getUser() != user) {
            throw new Api401Exception("다른 고객의 주문입니다");
        }

        if (!order.canBeCancelledByUser()) {
            throw new Api400Exception("취소할 수 없는 주문입니다");
        }

        UserPoint userPoint = userPointRepository.findByUserAndStore(user, store)
                .orElse(null);

        Coupon usedCoupon = order.getCoupon();

        UserCoupon userCoupon = null;
        if (usedCoupon != null) {
            userCoupon = userCouponRepository
                    .findByUserAndCoupon(user, usedCoupon)
                    .orElse(null);
        }

        order.cancel(userPoint, userCoupon);
    }

    @Transactional
    public void confirmOrder(User user, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 주문입니다"));

        Store store = order.getStore();

        if (order.getUser() != user) {
            throw new Api401Exception("다른 고객의 주문입니다");
        }

        if (!order.canBeConfirmedByUser()) {
            throw new Api400Exception("완료할 수 없는 주문입니다");
        }

        userPointRepository.findByUserAndStore(user, store)
                .ifPresentOrElse(
                        userPoint -> order.confirm(userPoint),
                        () -> {
                            UserPoint userPoint = new UserPoint(user, store, 0);
                            em.persist(userPoint);
                            order.confirm(userPoint);
                        }
                );
    }
}
