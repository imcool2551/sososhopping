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
    
    //쿠폰 리스트 조회 -> 발행중인 쿠폰/발행 예정 쿠폰 구분
//    @GetMapping(value = "/api/v1/owner/store/{storeId}/coupon")
//    public ResponseEntity readCouponList(@PathVariable(value = "storeId") Long storeId) {
//        List<StoreCouponResponseDto> expected = storeCouponService.readExceptedCouponList(storeId)
//                .stream()
//                .map(coupon -> new StoreCouponResponseDto(coupon, storeId))
//                .collect(Collectors.toList());
//
//        List<StoreCouponResponseDto> being = storeCouponService.readBeingCouponList(storeId)
//                .stream()
//                .map(coupon -> new StoreCouponResponseDto(coupon, storeId))
//                .collect(Collectors.toList());
//
//        StoreCouponListResponseDto dto = StoreCouponListResponseDto.builder()
//                .expected(expected)
//                .being(being)
//                .build();
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(dto);
//    }


    
    //쿠폰 삭제
//    @DeleteMapping(value = "/api/v1/owner/store/{storeId}/coupon/{couponId}")
//    public ResponseEntity deleteCoupon(@PathVariable(value = "storeId") Long storeId
//            , @PathVariable(value = "couponId") Long couponId) {
//        storeCouponService.deleteCoupon(storeId, couponId);
//
//        return new ResponseEntity(HttpStatus.OK);
//    }

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
