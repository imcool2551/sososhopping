package com.sososhopping.service.owner;

import com.sososhopping.common.dto.owner.response.UserCouponResponseDto;
import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.common.error.Api404Exception;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.domain.coupon.repository.CouponRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.entity.coupon.Coupon;
import com.sososhopping.entity.user.User;
import com.sososhopping.repository.coupon.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StoreCouponService {

    private final CouponRepository couponRepository;
    private final StoreRepository storeRepository;
    private final UserAuthRepository userRepository;
    private final UserCouponRepository userCouponRepository;
    private final EntityManager em;


    @Transactional
    public UserCouponResponseDto readUserCoupon(Long storeId, String phone, String couponCode) {
        User user = userRepository.findByPhone(phone).orElseThrow(() ->
                new Api400Exception("존재하지 않는 고객입니다"));

        Coupon coupon = couponRepository.findByCouponCode(couponCode).orElseThrow(() ->
                new Api400Exception("존재하지 않는 쿠폰입니다"));

        if (!Objects.equals(coupon.getStore().getId(), storeId))
            throw new Api404Exception("올바르지 않은 점포의 쿠폰입니다");

        UserCouponResponseDto dto = new UserCouponResponseDto(user, coupon, storeId);

        return dto;
    }

//    @Transactional
//    public void deleteCouponDirectly(Long storeId, String phone, String couponCode) {
//        User user = userRepository.findByPhone(phone).orElseThrow(() ->
//                new Api400Exception("존재하지 않는 고객입니다"));
//
//        Coupon coupon = couponRepository.findByCouponCode(couponCode).orElseThrow(() ->
//                new Api400Exception("존재하지 않는 쿠폰입니다"));
//
//        if (!Objects.equals(coupon.getStore().getId(), storeId))
//            throw new Api404Exception("올바르지 않은 점포의 쿠폰입니다");
//
//        UserCoupon userCoupon = userCouponRepository.findById(new UserCouponId(user.getId(), coupon.getId())).orElseThrow(() ->
//                new Api404Exception("고객이 쿠폰을 받은 기록이 없습니다"));
//
//        try {
//            userCoupon.use();
//        } catch (Api400Exception e) {
//            throw new Api403Exception("사용할 수 없는 쿠폰입니다");
//        }
//    }

}
