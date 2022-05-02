package com.sososhopping.domain.orders.service.user;

import com.sososhopping.a.user.request.order.CreateOrderDto;
import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.coupon.repository.CouponRepository;
import com.sososhopping.domain.coupon.repository.UserCouponRepository;
import com.sososhopping.domain.orders.repository.CartRepository;
import com.sososhopping.domain.point.repository.UserPointRepository;
import com.sososhopping.domain.store.repository.ItemRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.domain.user.repository.UserRepository;
import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.orders.OrderItem;
import com.sososhopping.entity.point.UserPoint;
import com.sososhopping.entity.store.Item;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.sososhopping.entity.orders.OrderType.DELIVERY;
import static java.time.LocalDateTime.now;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateOrderService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final CouponRepository couponRepository;
    private final UserPointRepository userPointRepository;
    private final UserCouponRepository userCouponRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final EntityManager em;

    public Long createOrder(Long userId, CreateOrderDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new NotFoundException("can't find store with id" + dto.getStoreId()));

        // 1. 영업 여부 검사
        if (!store.isOpen()) {
            throw new BadRequestException("영업중이 아닙니다");
        }

        // 2. 총 주문 금액 계산
        List<Item> items = findItems(store, dto);
        int orderPrice = calculateOrderPrice(items, dto.itemIdToQuantity());

        // 3. 포인트 사용
        int usedPoint = dto.getUsedPoint() != null
                ? useUserPoint(user, store, orderPrice, dto.getUsedPoint())
                : 0;

        // 4. 쿠폰 사용
        int couponDiscountPrice = 0;
        Coupon coupon = null;
        if (dto.getCouponId() != null) {
            coupon = couponRepository.findById(dto.getCouponId())
                    .orElseThrow(() -> new NotFoundException("can't find coupon with id " + dto.getCouponId()));

            UserCoupon userCoupon = userCouponRepository.findByUserAndCoupon(user, coupon)
                    .orElseThrow(() -> new NotFoundException("user does not have coupon with id " + dto.getCouponId()));

            userCoupon.use(store, orderPrice, now());
            couponDiscountPrice = coupon.calculateDiscountPrice(orderPrice);
        }

        // 5. 최종 가격 검증
        int deliveryCharge = dto.getOrderType() == DELIVERY
                ? store.getDeliveryCharge()
                : 0;
        int finalPrice = orderPrice - usedPoint - couponDiscountPrice + deliveryCharge;
        validateFinalPrice(dto, finalPrice);

        // 6. 주문 완료
        Order order = dto.toOrder(user, store, coupon, orderPrice);

        dto.itemIdToQuantity()
                .forEach((itemId, quantity) -> {
                    Item item = em.find(Item.class, itemId);
                    OrderItem orderItem =
                            new OrderItem(order, item, quantity, quantity * item.getPrice());
                    order.addOrderItem(orderItem);

                    cartRepository.findByUserAndItem(user, item)
                            .ifPresent(cartRepository::delete);
                });

        em.persist(order);
        return order.getId();
    }

    private List<Item> findItems(Store store, CreateOrderDto dto) {
        List<Long> itemIds = new ArrayList<>(dto.itemIdToQuantity().keySet());
        List<Item> findItems = itemRepository.findByIdIn(itemIds);

        boolean validIds = itemIds.size() == findItems.size();
        boolean availableItems = findItems.stream()
                .allMatch(item -> item.available(store));

        if (!validIds || !availableItems) {
            throw new BadRequestException("containing invalid item id");
        }

        return findItems;
    }

    private int calculateOrderPrice(List<Item> items, Map<Long, Integer> itemIdToQuantity) {
        return items.stream()
                .mapToInt(item -> itemIdToQuantity.get(item.getId()) * item.getPrice())
                .sum();
    }

    private int useUserPoint(User user, Store store, int orderPrice, int usedPoint) {
        if (orderPrice < usedPoint) {
            throw new BadRequestException("can't use more point than order price");
        }
        UserPoint userPoint = userPointRepository.findByUserAndStore(user, store)
                .orElseThrow(() -> new BadRequestException("user has no point at store"));
        userPoint.updatePoint(-usedPoint);
        return usedPoint;
    }


    private void validateFinalPrice(CreateOrderDto dto, int finalPrice) {
        if (finalPrice < 0) {
            throw new BadRequestException("final price can't be negative. finalPrice: " + finalPrice);
        }

        if (finalPrice != dto.getFinalPrice()) {
            throw new BadRequestException("final price mismatch. final price: " + finalPrice);
        }
    }
}
