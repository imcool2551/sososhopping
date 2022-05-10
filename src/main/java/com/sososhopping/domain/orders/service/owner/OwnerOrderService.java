package com.sososhopping.domain.orders.service.owner;

import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.domain.coupon.repository.UserCouponRepository;
import com.sososhopping.domain.orders.dto.owner.response.OrderListResponse;
import com.sososhopping.domain.orders.repository.OrderRepository;
import com.sososhopping.domain.owner.service.OwnerValidationService;
import com.sososhopping.domain.point.repository.UserPointRepository;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.orders.OrderStatus;
import com.sososhopping.entity.owner.Owner;
import com.sososhopping.entity.point.UserPoint;
import com.sososhopping.entity.store.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.sososhopping.entity.orders.OrderStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class OwnerOrderService {

    private final OwnerValidationService ownerValidationService;
    private final OrderRepository orderRepository;
    private final UserPointRepository userPointRepository;
    private final UserCouponRepository userCouponRepository;


    public List<OrderListResponse> findPendingOrders(Long ownerId, Long storeId) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);

        return orderRepository.findPendingOrders(store)
                .stream()
                .map(order -> new OrderListResponse(order.getUser(), store.getOwner(), order, store))
                .collect(Collectors.toList());
    }

    public List<OrderListResponse> findPickupOrders(Long ownerId, Long storeId, LocalDate date) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);

        return orderRepository.findTodayPickupOrders(store, date)
                .stream()
                .map(order -> new OrderListResponse(order.getUser(), store.getOwner(), order, store))
                .collect(Collectors.toList());
    }

    public List<OrderListResponse> findDeliveryOrders(Long ownerId, Long storeId, LocalDate date) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);

        return orderRepository.findTodayDeliveryOrders(store, date)
                .stream()
                .map(order -> new OrderListResponse(order.getUser(), store.getOwner(), order, store))
                .collect(Collectors.toList());
    }

    public List<OrderListResponse> findProcessedOrders(Long ownerId, Long storeId, LocalDate date) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);

        return orderRepository.findProcessedOrders(store, date)
                .stream()
                .map(order -> new OrderListResponse(order.getUser(), store.getOwner(), order, store))
                .collect(Collectors.toList());
    }

    public void updateOrderStatus(Long storeId, Long ownerId, Long orderId, OrderStatus status) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("no order with id " + orderId));

        Owner owner = store.getOwner();

        if (status == APPROVE) {
            order.approve(owner);
            return;
        } else if (status == READY) {
            order.ready(owner);
            return;
        } else if (status == REJECT) {
            UserPoint userPoint = userPointRepository.findByUserAndStore(order.getUser(), store)
                    .orElse(null);

            UserCoupon userCoupon = userCouponRepository.findByUserAndCoupon(order.getUser(), order.getCoupon())
                    .orElse(null);

            order.reject(owner, userPoint, userCoupon);
            return;
        }

        throw new BadRequestException("unknown request");
    }
}
