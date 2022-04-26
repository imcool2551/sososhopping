package com.sososhopping.service.user.order;

import com.sososhopping.common.dto.user.request.order.OrderCreateDto;
import com.sososhopping.common.dto.user.response.order.OrderListDto;
import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.common.error.Api401Exception;
import com.sososhopping.common.error.Api404Exception;
import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.user.User;
import com.sososhopping.entity.user.UserPoint;
import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.orders.OrderItem;
import com.sososhopping.entity.orders.OrderStatus;
import com.sososhopping.entity.orders.OrderType;
import com.sososhopping.entity.store.Item;
import com.sososhopping.entity.store.Store;
import com.sososhopping.repository.coupon.CouponRepository;
import com.sososhopping.repository.coupon.UserCouponRepository;
import com.sososhopping.repository.member.UserPointRepository;
import com.sososhopping.repository.order.CartRepository;
import com.sososhopping.repository.order.OrderRepository;
import com.sososhopping.domain.store.repository.ItemRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sososhopping.entity.orders.OrderStatus.PENDING;
import static com.sososhopping.entity.orders.OrderType.DELIVERY;
import static com.sososhopping.entity.orders.PaymentType.CASH;

@Service
@RequiredArgsConstructor
public class UserOrderService {

    private final StoreRepository storeRepository;
    private final CouponRepository couponRepository;
    private final UserPointRepository userPointRepository;
    private final UserCouponRepository userCouponRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final EntityManager em;

    // TODO: 절차지향적인 주문 비즈니스로직 객체지향적으로 리팩토링하기
    @Transactional
    public void createOrder(User user, OrderCreateDto dto) {

        Store findStore = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        if (!findStore.isOpen()) {
            throw new Api400Exception("영업중이 아닙니다");
        }

        // 배송 + 현장결제 거절
        if (dto.getOrderType() == DELIVERY && dto.getPaymentType() == CASH) {
            throw new Api400Exception("배송주문은 현장결제가 불가능합니다");
        }

        // 물품 검증
        List<Long> itemIds = dto.getOrderItems()
                .stream()
                .map(orderItemDto -> orderItemDto.getItemId())
                .collect(Collectors.toList());

        List<Item> requestedItems = itemRepository.findByIdIn(itemIds);

        boolean areValidItemIds = (itemIds.size() == requestedItems.size());
        boolean canBeProvidedBy = requestedItems.stream()
                .allMatch(item -> item.canBeProvidedBy(findStore));

        if (!areValidItemIds || !canBeProvidedBy) {
            throw new Api400Exception("구매할 수 없는 물품이 포함되어있습니다");
        }

        // 물품 가격
        int orderPrice = dto.getOrderItems()
                .stream()
                .mapToInt(orderItemDto ->
                        em.find(Item.class, orderItemDto.getItemId()).getPrice() *
                                orderItemDto.getQuantity()
                ).sum();

        // 유저 포인트 검증
        UserPoint userPoint = null;

        Integer usedPoint = Optional
                .ofNullable(dto.getUsedPoint())
                .orElse(0);

        if (usedPoint > orderPrice) {
            throw new Api400Exception("물품금액보다 포인트를 더 많이 사용할 수 없습니다");
        }

        if (usedPoint > 0) {
            userPoint = userPointRepository
                    .findByUserAndStore(user, findStore)
                    .orElseThrow(() -> new Api400Exception("포인트가 없습니다"));

            if (!userPoint.hasMoreThan(usedPoint)) {
                throw new Api400Exception("포인트가 부족합니다");
            }

            userPoint.usePoint(usedPoint);
        }

        // 쿠폰 검증
        Integer couponDiscountPrice = 0;
        Coupon findCoupon = null;
        if (dto.getCouponId() != null) {
            findCoupon = couponRepository
                    .findById(dto.getCouponId())
                    .orElseThrow(() -> new Api404Exception("존재하지 않는 쿠폰입니다"));

            if (!findCoupon.belongsTo(findStore)) {
                throw new Api400Exception("해당 점포의 쿠폰이 아닙니다");
            }

            if (findCoupon.minimumPriceGreaterThan(orderPrice)) {
                throw new Api400Exception("주문 금액이 쿠폰 최소주문금액보다 적습니다");
            }

            couponDiscountPrice = findCoupon.getDiscountPrice(orderPrice);

            UserCoupon userCoupon = userCouponRepository
                    .findByUserAndCoupon(user, findCoupon)
                    .orElseThrow(() -> new Api404Exception("유저에게 쿠폰이 없습니다"));
            userCoupon.use();
        }

        // 최종 가격 계산
        int finalPrice = orderPrice - usedPoint - couponDiscountPrice;
        int deliveryCharge = 0;
        OrderType orderType = dto.getOrderType();

        if (orderType == DELIVERY) {
            deliveryCharge = findStore.getDeliveryCharge();
            finalPrice += deliveryCharge;
        }

        if (finalPrice < 0) {
            throw new Api400Exception("최종금액은 0원 이상이어야 합니다");
        }

        if (finalPrice != dto.getFinalPrice()) {
            throw new Api400Exception("가격이 맞지 않습니다");
        }

        // 주문 완료
        Order order = Order.builder()
                .user(user)
                .ordererName(dto.getOrdererName())
                .ordererPhone(dto.getOrdererPhone())
                .orderType(orderType)
                .visitDate(dto.getVisitDate())
                .store(findStore)
                .deliveryCharge(deliveryCharge)
                .deliveryStreetAddress(dto.getDeliveryStreetAddress())
                .deliveryDetailedAddress(dto.getDeliveryDetailedAddress())
                .paymentType(dto.getPaymentType())
                .orderPrice(orderPrice)
                .usedPoint(usedPoint)
                .coupon(findCoupon)
                .finalPrice(finalPrice)
                .orderStatus(PENDING)
                .build();

        dto.getOrderItems()
                .forEach(orderItemDto -> {
                    Item item = em.find(Item.class, orderItemDto.getItemId());
                    OrderItem orderItem = OrderItem.builder()
                            .order(order)
                            .item(item)
                            .quantity(orderItemDto.getQuantity())
                            .totalPrice(item.getPrice() * orderItemDto.getQuantity())
                            .build();

                    cartRepository.findByUserAndItem(user, item)
                                    .ifPresent(cart -> em.remove(cart));

                    orderItem.setOrder(order);
                });

        em.persist(order);
    }

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
                        userPoint -> {
                            order.confirm(userPoint);
                        },
                        () -> {
                            UserPoint userPoint = new UserPoint(user, store, 0);
                            em.persist(userPoint);
                            order.confirm(userPoint);
                        }
                );
    }
}
