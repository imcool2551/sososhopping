package com.sososhopping.domain.orders.service.user;

import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.domain.orders.dto.user.request.CreateOrderDto;
import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.CouponType;
import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.point.UserPoint;
import com.sososhopping.entity.store.Item;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.sososhopping.domain.orders.dto.user.request.CreateOrderDto.CreateOrderItemDto;
import static com.sososhopping.entity.orders.OrderType.DELIVERY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderProcessorTest {

    @Test
    void create_WhenStoreClosed_ThrowsBadRequestException() {
        Store store = buildOpenStore();
        store.updateOpen(false);

        assertThatThrownBy(() -> new OrderProcessor(store, null, null))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void create_WhenContainsInvalidItem_ThrowsBadRequestException() {
        Store storeA = buildOpenStore();
        Store storeB = buildOpenStore();
        Item itemB = buildItem(storeB, true, 10000);
        CreateOrderDto dto = new CreateOrderDto();
        dto.setOrderItems(List.of(new CreateOrderItemDto(itemB.getId(), 1)));

        assertThatThrownBy(() -> new OrderProcessor(storeA, List.of(itemB), dto))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void create_WhenContainsNotOnSaleItem_ThrowsBadRequestException() {
        Store store = buildOpenStore();
        Item item = buildItem(store, false, 10000);
        CreateOrderDto dto = new CreateOrderDto();
        dto.setOrderItems(List.of(new CreateOrderItemDto(item.getId(), 1)));

        assertThatThrownBy(() -> new OrderProcessor(store, List.of(item), dto))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void useUserPoint_WhenUsedPointExceedsOrderPrice_ThrowsBadRequestException() {
        int itemPrice = 10000;
        int quantity = 2;
        int usedPoint = 5 * itemPrice;

        Store store = buildOpenStore();
        Item item = buildItem(store, true, itemPrice);
        CreateOrderDto dto = new CreateOrderDto();
        dto.setOrderItems(List.of(new CreateOrderItemDto(item.getId(), quantity)));
        dto.setUsedPoint(usedPoint);
        OrderProcessor orderProcessor = new OrderProcessor(store, List.of(item), dto);

        assertThatThrownBy(() -> orderProcessor.useUserPoint(null))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void useUserPoint() {
        int initialPoint = 80000;
        int itemPrice = 10000;
        int quantity = 2;
        int usedPoint = 5000;

        Store store = buildOpenStore();
        UserPoint userPoint = new UserPoint(buildUser(), store, initialPoint);
        Item item = buildItem(store, true, itemPrice);
        CreateOrderDto dto = new CreateOrderDto();
        dto.setOrderItems(List.of(new CreateOrderItemDto(item.getId(), quantity)));
        dto.setUsedPoint(usedPoint);
        OrderProcessor orderProcessor = new OrderProcessor(store, List.of(item), dto);

        orderProcessor.useUserPoint(userPoint);

        assertThat(userPoint.getPoint()).isEqualTo(75000);
        assertThat(userPoint.getUserPointLogs()).hasSize(1);
    }

    @Test
    void buildOrder_WhenFinalPriceMisMatch_ThrowsBadRequestException() {
        int itemPrice = 10000;
        int quantity = 1;

        Store store = buildOpenStore();
        Item item = buildItem(store, true, itemPrice);
        CreateOrderDto dto = new CreateOrderDto();
        dto.setOrderItems(List.of(new CreateOrderItemDto(item.getId(), quantity)));
        dto.setFinalPrice(itemPrice * quantity + 13579);
        OrderProcessor orderProcessor = new OrderProcessor(store, List.of(item), dto);

        assertThatThrownBy(() -> orderProcessor.buildOrder(buildUser(), null))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void buildOrder() {
        int itemPrice = 10000;
        int quantity = 3;
        int usedPoint = 1000;
        int couponAmount = 1000;
        int deliveryCharge = 1000;
        int orderPrice = itemPrice * quantity;
        int finalPrice = itemPrice * quantity - usedPoint - couponAmount + deliveryCharge;

        Store store = Store.builder()
                .isOpen(true)
                .deliveryStatus(true)
                .deliveryCharge(deliveryCharge)
                .build();
        Item item = buildItem(store, true, itemPrice);
        Coupon coupon = buildFixCoupon(couponAmount);
        CreateOrderDto dto = new CreateOrderDto();
        dto.setOrderItems(List.of(new CreateOrderItemDto(item.getId(), quantity)));
        dto.setUsedPoint(usedPoint);
        dto.setCouponId(coupon.getId());
        dto.setOrderType(DELIVERY);
        dto.setFinalPrice(finalPrice);
        OrderProcessor orderProcessor = new OrderProcessor(store, List.of(item), dto);

        Order order = orderProcessor.buildOrder(buildUser(), coupon);

        assertThat(order.getOrderItems()).hasSize(1);
        assertThat(order.getCoupon()).isEqualTo(coupon);
        assertThat(order.getDeliveryCharge()).isEqualTo(deliveryCharge);
        assertThat(order.getOrderPrice()).isEqualTo(orderPrice);
        assertThat(order.getFinalPrice()).isEqualTo(finalPrice);
    }

    private Store buildOpenStore() {
        return Store.builder()
                .isOpen(true)
                .build();
    }

    public Item buildItem(Store store, boolean onSale, int price) {
        return Item.builder()
                .store(store)
                .onSale(onSale)
                .price(price)
                .build();
    }

    private User buildUser() {
        return User.builder().build();
    }

    private Coupon buildFixCoupon(int couponAmount) {
        return Coupon.builder()
                .couponType(CouponType.FIX)
                .amount(couponAmount)
                .build();
    }
}
