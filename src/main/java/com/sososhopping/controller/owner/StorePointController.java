package com.sososhopping.controller.owner;

import com.sososhopping.common.dto.owner.response.StoreUserPointResponseDto;
import com.sososhopping.service.owner.StorePointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

//@RestController
@RequiredArgsConstructor
public class StorePointController {

    private final StorePointService storePointService;

    //고객 포인트 직접 변경
//    @PostMapping(value = "/api/v1/owner/store/{storeId}/point/local")
//    public ResponseEntity updateUserPointDirectly(
//            @PathVariable(value = "storeId") Long storeId
//            , @RequestBody UserPointUpdateRequestDto dto) {
//        storePointService.updateUserPointDirectly(storeId, dto);
//
//        return new ResponseEntity(HttpStatus.OK);
//    }

    //고객 포인트 중도 조회
    @GetMapping(value = "/api/v1/owner/store/{storeId}/point/local")
    public ResponseEntity getUserPoint(
            @PathVariable(value = "storeId") Long storeId
            , @RequestParam String userPhone) {
        StoreUserPointResponseDto dto = storePointService.readUserPoint(storeId, userPhone);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }
}
