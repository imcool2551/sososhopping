package com.sososhopping.server.service.owner;

import com.sososhopping.server.common.dto.owner.request.StoreCouponRequestDto;
import com.sososhopping.server.common.error.Api400Exception;
import com.sososhopping.server.entity.coupon.*;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.repository.coupon.CouponRepository;
import com.sososhopping.server.repository.coupon.UserCouponRepository;
import com.sososhopping.server.repository.member.UserRepository;
import com.sososhopping.server.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class StoreCouponService {

    private final CouponRepository couponRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final UserCouponRepository userCouponRepository;
    private final EntityManager em;

    @Transactional
    public List<Coupon> readExceptedCouponList(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        List<Coupon> exceptedCoupons
                = couponRepository.findExceptedByStoreAndIssuedStartDateAfter(store, LocalDateTime.now());

        return exceptedCoupons;
    }

    @Transactional
    public List<Coupon> readBeingCouponList(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        LocalDateTime date = LocalDateTime.now();

        List<Coupon> beingCoupons
                = couponRepository.findBeingByStoreAndIssuedStartDateBeforeAndIssuedDueDateAfter(store, date, date);

        return beingCoupons;
    }

    @Transactional
    public void createCoupon(Long storeId, StoreCouponRequestDto dto) {
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        String couponCode = createCode();

        if (dto.getCouponType().equals("FIX")) {
            FixCoupon coupon = new FixCoupon(store, dto, couponCode);
            em.persist(coupon);
        } else if (dto.getCouponType().equals("RATE")) {
            RateCoupon coupon = new RateCoupon(store, dto, couponCode);
            em.persist(coupon);
        } else {
            throw new Api400Exception("쿠폰 형식이 올바르지 않습니다");
        }
    }

    @Transactional
    public Coupon readCoupon(Long storeId, Long couponId) {
        return couponRepository.findById(couponId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 쿠폰입니다"));
    }

    @Transactional
    public void updateCoupon(Long storeId, Long couponId, StoreCouponRequestDto dto) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 쿠폰입니다"));

        if (coupon.getIssuedStartDate().isAfter(LocalDateTime.now())) {
            coupon.update(dto);
        }
    }

    @Transactional
    public void deleteCoupon(Long storeId, Long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 쿠폰입니다"));

        if (coupon.getExpiryDate().isBefore(LocalDateTime.now())
                || coupon.getIssuedStartDate().isAfter(LocalDateTime.now())) {
            couponRepository.delete(coupon);
        } else {
            throw new Api400Exception("아직 삭제할 수 없는 쿠폰입니다");
        }
    }

    @Transactional
    public void deleteCouponDirectly(Long storeId, String phone, String couponCode) {
        User user = userRepository.findByPhone(phone).orElseThrow(() ->
                new Api400Exception("존재하지 않는 고객입니다"));

        Coupon coupon = couponRepository.findByCouponCode(couponCode).orElseThrow(() ->
                new Api400Exception("존재하지 않는 쿠폰입니다"));

        if (coupon.getStore().getId() != storeId)
            throw new Api400Exception("올바르지 않은 점포의 쿠폰입니다");

        UserCoupon userCoupon = userCouponRepository.findById(new UserCouponId(user, coupon)).orElseThrow(() ->
                new Api400Exception("고객이 쿠폰을 받은 기록이 없습니다"));

        if (userCoupon.getUsed() == true) {
            throw new Api400Exception("이미 해당 고객이 사용한 쿠폰입니다");
        } else {
            userCoupon.setUsed(true);
        }
    }

    private String createCode() {
        final char[] codeCharacters = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
                , 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N'
                , 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        Random random = new Random(System.currentTimeMillis());
        StringBuilder codeBuilder = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            codeBuilder.append(codeCharacters[random.nextInt(36)]);
        }

        return codeBuilder.toString();
    }
}