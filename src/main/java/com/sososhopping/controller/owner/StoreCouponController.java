package com.sososhopping.controller.owner;

import com.sososhopping.common.dto.owner.response.UserCouponResponseDto;
import com.sososhopping.service.owner.StoreCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreCouponController {

    private final StoreCouponService storeCouponService;


    //고객 쿠폰 중도 조회
    @GetMapping(value = "/api/v1/owner/store/{storeId}/coupon/local")
    public ResponseEntity readUserCoupon(@PathVariable(value = "storeId") Long storeId
            , @RequestParam("phone") String phone
            , @RequestParam("couponCode") String couponCode) {
        UserCouponResponseDto dto = storeCouponService.readUserCoupon(storeId, phone, couponCode);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }

    //고객 쿠폰 직접 삭제
//    @PostMapping(value = "/api/v1/owner/store/{storeId}/coupon/local")
//    public ResponseEntity deleteCouponDirectly(
//            @PathVariable(value = "storeId") Long storeId
//            , @RequestBody UserCouponUsageRequestDto dto) {
//        storeCouponService.deleteCouponDirectly(storeId, dto.getPhone(), dto.getCouponCode());
//
//        return new ResponseEntity(HttpStatus.OK);
//    }
}
