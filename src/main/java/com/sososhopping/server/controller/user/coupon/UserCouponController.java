package com.sososhopping.server.controller.user.coupon;

import com.sososhopping.server.common.dto.ApiResponse;
import com.sososhopping.server.common.dto.user.response.store.CouponDto;
import com.sososhopping.server.common.error.Api404Exception;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.repository.coupon.CouponRepository;
import com.sososhopping.server.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserCouponController {

    private final StoreRepository storeRepository;

    @GetMapping("/api/v1/stores/{storeId}/coupons")
    public ApiResponse<CouponDto> getStoreCoupons(@PathVariable Long storeId, Authentication authentication) {

        Store findStore = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        List<CouponDto> couponListDto = findStore.getCoupons()
                .stream()
                .map(coupon -> new CouponDto(coupon))
                .collect(Collectors.toList());

        return new ApiResponse<CouponDto>(couponListDto);
    }
}
