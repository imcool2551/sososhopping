package com.sososhopping.domain.orders.service.user;

import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.coupon.repository.CouponRepository;
import com.sososhopping.domain.coupon.repository.UserCouponRepository;
import com.sososhopping.domain.orders.dto.user.request.CreateOrderDto;
import com.sososhopping.domain.orders.repository.CartRepository;
import com.sososhopping.domain.point.repository.UserPointRepository;
import com.sososhopping.domain.store.repository.ItemRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.domain.user.repository.UserRepository;
import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.coupon.UserCoupon;
import com.sososhopping.entity.orders.Order;
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
        List<Item> items = findItems(dto);
        OrderProcessor orderProcessor = new OrderProcessor(store, items, dto);

        if (dto.getUsedPoint() != null) {
            UserPoint userPoint = userPointRepository.findByUserAndStore(user, store)
                    .orElseThrow(() -> new BadRequestException("user has no point at store"));
            orderProcessor.useUserPoint(userPoint);
        }

        Coupon coupon = null;
        if (dto.getCouponId() != null) {
            coupon = couponRepository.findById(dto.getCouponId())
                    .orElseThrow(() -> new NotFoundException("can't find coupon with id " + dto.getCouponId()));

            UserCoupon userCoupon = userCouponRepository.findByUserAndCoupon(user, coupon)
                    .orElseThrow(() -> new NotFoundException("user does not have coupon with id " + dto.getCouponId()));

            orderProcessor.useUserCoupon(userCoupon, now());
        }

        Order order = orderProcessor.buildOrder(user, coupon);
        em.persist(order);
        items.forEach(item -> cartRepository.findByUserAndItem(user, item)
                .ifPresent(cartRepository::delete));
        return order.getId();
    }

    private List<Item> findItems(CreateOrderDto dto) {
        List<Long> itemIds = new ArrayList<>(dto.itemIdToQuantity().keySet());
        return itemRepository.findByIdIn(itemIds);
    }
}
