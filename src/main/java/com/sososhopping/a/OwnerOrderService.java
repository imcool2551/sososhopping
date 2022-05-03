package com.sososhopping.a;

import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.common.error.Api403Exception;
import com.sososhopping.common.error.Api404Exception;
import com.sososhopping.domain.auth.repository.OwnerAuthRepository;
import com.sososhopping.domain.coupon.repository.UserCouponRepository;
import com.sososhopping.domain.orders.repository.OrderRepository;
import com.sososhopping.domain.point.repository.UserPointRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.orders.OrderStatus;
import com.sososhopping.entity.owner.Owner;
import com.sososhopping.entity.point.UserPoint;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static com.sososhopping.entity.orders.OrderStatus.*;

//@Service
@RequiredArgsConstructor
public class OwnerOrderService {

    private final OwnerAuthRepository ownerRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;
    private final UserPointRepository userPointRepository;
    private final UserCouponRepository userCouponRepository;

    @Transactional
    public void handleOrder(
            Long ownerId,
            Long storeId,
            Long orderId,
            OrderStatus action
    ) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() ->
                new Api404Exception("존재하지 않는 점주입니다"));

        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new Api404Exception("존재하지 않는 점포입니다"));

        Order order = orderRepository.findOrderDetailsById(orderId).orElseThrow(() ->
                new Api404Exception("존재하지 않는 주문입니다"));

        if (order.getStore() != store) {
            throw new Api400Exception("점포의 주문이 아닙니다");
        }

        if (order.getStore().getOwner() != owner) {
            throw new Api403Exception("권한이 없습니다");
        }

        if (action == APPROVE) {
            order.approve();
            return;
        } else if (action == READY) {
            order.ready();
            return;
        }

        User user = order.getUser();

        UserPoint userPoint = userPointRepository.findByUserAndStore(user, store)
                .orElse(null);

        Coupon usedCoupon = order.getCoupon();

        UserCoupon userCoupon = null;
        if (usedCoupon != null) {
            userCoupon = userCouponRepository.findByUserAndCoupon(user, usedCoupon)
                    .orElse(null);
        }

        if (action == REJECT) {
            order.reject(userPoint, userCoupon);
            return;
        }
    }
}
