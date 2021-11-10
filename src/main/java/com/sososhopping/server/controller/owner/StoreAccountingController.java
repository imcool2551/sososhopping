package com.sososhopping.server.controller.owner;

import com.sososhopping.server.common.dto.owner.request.StoreAccountingRequestDto;
import com.sososhopping.server.common.dto.owner.response.StoreAccountingResponseDto;
import com.sososhopping.server.entity.store.Accounting;
import com.sososhopping.server.service.owner.StoreAccountingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class StoreAccountingController {

    private final StoreAccountingService storeAccountingService;

    //조회 -> 연도, 월 기반
    @GetMapping(value = "/api/v1/owner/store/{storeId}/accounting")
    public ResponseEntity readAccountingList(
            @PathVariable(value = "storeId") Long storeId
            , @RequestParam String yearMonth) {

        List<StoreAccountingResponseDto> accountings = storeAccountingService.readAccountingList(storeId, yearMonth)
                .stream()
                .map(accounting -> new StoreAccountingResponseDto(accounting))
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountings);
    }

    @PostMapping(value = "/api/v1/owner/store/{storeId}/accounting")
    public ResponseEntity createAccounting(
            @PathVariable(value = "storeId") Long storeId,
            @RequestBody StoreAccountingRequestDto dto) {
        storeAccountingService.createAccounting(storeId, dto);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    //상세 조회
    @GetMapping(value = "/api/v1/owner/store/{storeId}/accounting/{accountingId}")
    public ResponseEntity readAccounting(
            @PathVariable(value = "storeId") Long storeId
            , @PathVariable(value = "accountingId") Long accountingId) {
        Accounting accounting = storeAccountingService.readAccounting(storeId, accountingId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new StoreAccountingResponseDto(accounting));
    }

    //수정
    @PatchMapping(value = "/api/v1/owner/store/{storeId}/accounting/{accountingId}")
    public ResponseEntity updateAccounting(
            @PathVariable(value = "storeId") Long storeId
            , @PathVariable(value = "accountingId") Long accountingId
            , @RequestBody StoreAccountingRequestDto dto) {
        storeAccountingService.updateAccounting(storeId, accountingId, dto);

        return new ResponseEntity(HttpStatus.OK);
    }

    //삭제
    @DeleteMapping(value = "/api/v1/owner/store/{storeId}/accounting/{accountingId}")
    public ResponseEntity deleteAccounting(
            @PathVariable(value = "storeId") Long storeId
            , @PathVariable(value = "accountingId") Long accountingId) {
        storeAccountingService.deleteAccounting(storeId, accountingId);

        return new ResponseEntity(HttpStatus.OK);
    }
}
