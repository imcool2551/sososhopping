package com.sososhopping.controller.owner;

import com.sososhopping.service.owner.StoreCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreCouponController {

    private final StoreCouponService storeCouponService;



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
