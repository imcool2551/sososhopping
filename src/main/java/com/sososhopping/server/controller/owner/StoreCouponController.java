package com.sososhopping.server.controller.owner;

import com.sososhopping.server.common.dto.owner.request.UserCouponUsageRequestDto;
import com.sososhopping.server.common.dto.owner.request.StoreCouponRequestDto;
import com.sososhopping.server.common.dto.owner.response.StoreCouponListResponseDto;
import com.sososhopping.server.common.dto.owner.response.StoreCouponResponseDto;
import com.sososhopping.server.common.dto.owner.response.UserCouponResponseDto;
import com.sososhopping.server.entity.coupon.Coupon;
import com.sososhopping.server.service.owner.StoreCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class StoreCouponController {

    private final StoreCouponService storeCouponService;
    
    //쿠폰 리스트 조회 -> 발행중인 쿠폰/발행 예정 쿠폰 구분
    @GetMapping(value = "/api/v1/owner/store/{storeId}/coupon")
    public ResponseEntity readCouponList(@PathVariable(value = "storeId") Long storeId) {
        List<StoreCouponResponseDto> expected = storeCouponService.readExceptedCouponList(storeId)
                .stream()
                .map(coupon -> new StoreCouponResponseDto(coupon, storeId))
                .collect(Collectors.toList());

        List<StoreCouponResponseDto> being = storeCouponService.readBeingCouponList(storeId)
                .stream()
                .map(coupon -> new StoreCouponResponseDto(coupon, storeId))
                .collect(Collectors.toList());

        StoreCouponListResponseDto dto = StoreCouponListResponseDto.builder()
                .expected(expected)
                .being(being)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }
    
    //쿠폰 생성
    @PostMapping(value = "/api/v1/owner/store/{storeId}/coupon")
    public ResponseEntity createCoupon(@PathVariable(value = "storeId") Long storeId
            , @RequestBody StoreCouponRequestDto dto) {
        storeCouponService.createCoupon(storeId, dto);

        return new ResponseEntity(HttpStatus.CREATED);
    }
    
    //쿠폰 조회
    @GetMapping(value = "/api/v1/owner/store/{storeId}/coupon/{couponId}")
    public ResponseEntity readCoupon(@PathVariable(value = "storeId") Long storeId
            , @PathVariable(value = "couponId") Long couponId) {
        Coupon coupon = storeCouponService.readCoupon(storeId, couponId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new StoreCouponResponseDto(coupon, storeId));
    }

    //쿠폰 수정
    @PatchMapping(value = "/api/v1/owner/store/{storeId}/coupon/{couponId}")
    public ResponseEntity updateCoupon(@PathVariable(value = "storeId") Long storeId
            , @PathVariable(value = "couponId") Long couponId
            , @RequestBody StoreCouponRequestDto dto) {
        storeCouponService.updateCoupon(storeId, couponId, dto);

        return new ResponseEntity(HttpStatus.OK);
    }
    
    //쿠폰 삭제
    @DeleteMapping(value = "/api/v1/owner/store/{storeId}/coupon/{couponId}")
    public ResponseEntity deleteCoupon(@PathVariable(value = "storeId") Long storeId
            , @PathVariable(value = "couponId") Long couponId) {
        storeCouponService.deleteCoupon(storeId, couponId);

        return new ResponseEntity(HttpStatus.OK);
    }

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
    @DeleteMapping(value = "/api/v1/owner/store/{storeId}/coupon/local")
    public ResponseEntity deleteCouponDirectly(
            @PathVariable(value = "storeId") Long storeId
            , @RequestBody UserCouponUsageRequestDto dto) {
        storeCouponService.deleteCouponDirectly(storeId, dto.getPhone(), dto.getCouponCode());

        return new ResponseEntity(HttpStatus.OK);
    }
}
