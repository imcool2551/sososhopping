package com.sososhopping.server.controller.user.coupon;

import com.sososhopping.server.common.dto.ApiResponse;
import com.sososhopping.server.common.dto.user.request.coupon.CouponRegisterDto;
import com.sososhopping.server.common.dto.user.response.store.CouponDto;
import com.sososhopping.server.common.error.Api401Exception;
import com.sososhopping.server.common.error.Api404Exception;
import com.sososhopping.server.entity.coupon.Coupon;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.repository.coupon.CouponRepository;
import com.sososhopping.server.repository.member.UserRepository;
import com.sososhopping.server.repository.store.StoreRepository;
import com.sososhopping.server.service.user.coupon.UserCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserCouponController {

    private final UserCouponService userCouponService;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final CouponRepository couponRepository;

    @GetMapping("/api/v1/stores/{storeId}/coupons")
    public ApiResponse<CouponDto> getStoreCoupons(@PathVariable Long storeId, Authentication authentication) {

        Store findStore = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));


        LocalDateTime now = LocalDateTime.now();
        List<Coupon> coupons = couponRepository
                .findBeingByStoreAndIssuedStartDateBeforeAndIssuedDueDateAfter(findStore, now, now);

        List<CouponDto> couponListDto = coupons
                .stream()
                .map(coupon -> new CouponDto(coupon))
                .collect(Collectors.toList());

        return new ApiResponse<CouponDto>(couponListDto);
    }

    @PostMapping("/api/v1/users/my/coupons")
    public ResponseEntity registerCoupon(
            Authentication authentication,
            @RequestBody CouponRegisterDto dto
    ) {
        Long userId = Long.parseLong(authentication.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid Token"));

        if (dto.getCouponId() != null) {
            userCouponService.registerCouponByCouponId(user, dto.getCouponId());
        } else if (dto.getCouponCode() != null) {
            userCouponService.registerCouponByCouponCode(user, dto.getCouponCode());
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }
}

