package com.sososhopping.domain.orders.service.user;

import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.domain.orders.dto.user.request.CreateOrderDto;
import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.orders.OrderItem;
import com.sososhopping.entity.point.UserPoint;
import com.sososhopping.entity.store.Item;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;

import java.time.LocalDateTime;
import java.util.List;

import static com.sososhopping.entity.orders.OrderType.DELIVERY;

public class OrderProcessor {

    private final Store store;
    private final List<Item> items;
    private final CreateOrderDto dto;

    public OrderProcessor(Store store, List<Item> items, CreateOrderDto dto) {
        validateStoreOpen(store);
        validateItems(store, items, dto);
        this.store = store;
        this.items = items;
        this.dto = dto;
    }

    private void validateStoreOpen(Store store) {
        if (!store.isOpen()) {
            throw new BadRequestException("영업중이 아닙니다");
        }
    }

    private void validateItems(Store store, List<Item> items, CreateOrderDto dto) {
        boolean validIds = dto.itemIds().size() == items.size();
        boolean availableItems = items.stream()
                .allMatch(item -> item.available(store));

        if (!validIds || !availableItems) {
            throw new BadRequestException("containing invalid item id");
        }
    }

    public void useUserPoint(UserPoint userPoint) {
        if (calculateOrderPrice() < dto.getUsedPoint()) {
            throw new BadRequestException("can't use more point than order price");
        }
        userPoint.updatePoint(-dto.getUsedPoint());
    }

    public void useUserCoupon(UserCoupon userCoupon, LocalDateTime at) {
        userCoupon.use(store, calculateOrderPrice(), at);
    }

    public Order buildOrder(User user, Coupon coupon) {
        int orderPrice = calculateOrderPrice();
        int pointDiscountPrice = dto.getUsedPoint() != null
                ? dto.getUsedPoint()
                : 0;
        int couponDiscountPrice = coupon != null
                ? coupon.calculateDiscountPrice(orderPrice)
                : 0;
        int deliveryCharge = dto.getOrderType() == DELIVERY
                ? store.getDeliveryCharge()
                : 0;
        int finalPrice = orderPrice - pointDiscountPrice - couponDiscountPrice + deliveryCharge;
        validateFinalPrice(finalPrice);
        Order order = dto.toOrder(user, store, coupon, finalPrice, orderPrice, deliveryCharge);
        addOrderItem(order);
        return order;
    }

    private int calculateOrderPrice() {
        return items.stream()
                .mapToInt(item -> dto.itemIdToQuantity().get(item.getId())
                        * item.getPrice())
                .sum();
    }

    private void validateFinalPrice(int finalPrice) {
        if (finalPrice < 0) {
            throw new BadRequestException("final price can't be negative. finalPrice: " + finalPrice);
        }

        if (finalPrice != dto.getFinalPrice()) {
            throw new BadRequestException("final price mismatch. final price: " + finalPrice);
        }
    }

    private void addOrderItem(Order order) {
        dto.itemIdToQuantity()
                .forEach((itemId, quantity) -> {
                    Item item = items.stream()
                            .filter(i -> i.matchId(itemId))
                            .findAny()
                            .orElseThrow(NotFoundException::new);
                    OrderItem orderItem =
                            new OrderItem(order, item, quantity, quantity * item.getPrice());

                    orderItem.toOrder(order);
                });
    }
}
