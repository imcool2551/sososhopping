package com.sososhopping.domain.orders.service.user;

import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.coupon.repository.UserCouponRepository;
import com.sososhopping.domain.orders.repository.OrderRepository;
import com.sososhopping.domain.point.repository.UserPointRepository;
import com.sososhopping.domain.user.repository.UserRepository;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.point.UserPoint;
import com.sososhopping.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateOrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final UserPointRepository userPointRepository;
    private final UserCouponRepository userCouponRepository;

    public void cancelOrder(Long userId, Long orderId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("no order with id " + orderId));

        UserPoint userPoint = userPointRepository.findByUserAndStore(user, order.getStore())
                .orElse(null);

        UserCoupon userCoupon = userCouponRepository.findByUserAndCoupon(user, order.getCoupon())
                .orElse(null);

        order.cancel(user, userPoint, userCoupon);
    }

    public void confirmOrder(Long userId, Long orderId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("no order with id " + orderId));

        userPointRepository.findByUserAndStore(user, order.getStore())
                .ifPresentOrElse(
                        userPoint -> order.confirm(user, userPoint),
                        () -> {
                            UserPoint userPoint = new UserPoint(user, order.getStore(), 0);
                            userPointRepository.save(userPoint);
                            order.confirm(user, userPoint);
                        });
    }
}
