package com.sososhopping.server.service.owner;

import com.sososhopping.server.common.error.Api400Exception;
import com.sososhopping.server.common.error.Api403Exception;
import com.sososhopping.server.common.error.Api404Exception;
import com.sososhopping.server.entity.coupon.Coupon;
import com.sososhopping.server.entity.coupon.UserCoupon;
import com.sososhopping.server.entity.member.Owner;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.member.UserPoint;
import com.sososhopping.server.entity.orders.Order;
import com.sososhopping.server.entity.orders.OrderStatus;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.repository.coupon.UserCouponRepository;
import com.sososhopping.server.repository.member.OwnerRepository;
import com.sososhopping.server.repository.member.UserPointRepository;
import com.sososhopping.server.repository.order.OrderRepository;
import com.sososhopping.server.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.sososhopping.server.entity.orders.OrderStatus.*;

@Service
@RequiredArgsConstructor
public class StoreOrderService {

    private final OwnerRepository ownerRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;
    private final UserPointRepository userPointRepository;
    private final UserCouponRepository userCouponRepository;

    @Transactional
    public List<Order> findPendingOrders(Long ownerId, Long storeId) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() ->
                new Api404Exception("존재하지 않는 점주입니다"));

        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new Api404Exception("존재하지 않는 점포입니다"));

        if (store.getOwner() != owner) {
            throw new Api403Exception("다른 점주의 점포입니다");
        }

        return orderRepository.findPendingOrdersByStore(store);
    }

    @Transactional
    public List<Order> findPickupOrders(Long ownerId, Long storeId) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() ->
                new Api404Exception("존재하지 않는 점주입니다"));

        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new Api404Exception("존재하지 않는 점포입니다"));

        if (store.getOwner() != owner) {
            throw new Api403Exception("다른 점주의 점포입니다");
        }

        return orderRepository.findPickupOrdersByStore(store);
    }

    @Transactional
    public List<Order> findDeliveryOrders(Long ownerId, Long storeId) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() ->
                new Api404Exception("존재하지 않는 점주입니다"));

        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new Api404Exception("존재하지 않는 점포입니다"));

        if (store.getOwner() != owner) {
            throw new Api403Exception("다른 점주의 점포입니다");
        }

        return orderRepository.findDeliveryOrdersByStore(store);
    }

    @Transactional
    public List<Order> findDoneOrdersByDate(Long ownerId, Long storeId, LocalDate date) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() ->
                new Api404Exception("존재하지 않는 점주입니다"));

        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new Api404Exception("존재하지 않는 점포입니다"));

        if (store.getOwner() != owner) {
            throw new Api403Exception("다른 점주의 점포입니다");
        }

        return orderRepository.findDoneOrdersByStoreAndDate(store, date);
    }

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

        Order order = orderRepository.findById(orderId).orElseThrow(() ->
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