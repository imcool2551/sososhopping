package com.sososhopping.controller.owner;

import com.sososhopping.common.dto.owner.response.StoreAccountingResponseDto;
import com.sososhopping.entity.store.Accounting;
import com.sososhopping.service.owner.StoreAccountingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreAccountingController {

    private final StoreAccountingService storeAccountingService;

    //상세 조회
    @GetMapping(value = "/api/v1/owner/store/{storeId}/accounting/{accountingId}")
    public ResponseEntity readAccounting(
            @PathVariable(value = "storeId") Long storeId
            , @PathVariable(value = "accountingId") Long accountingId) {
        Accounting accounting = storeAccountingService.readAccounting(storeId, accountingId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new StoreAccountingResponseDto(accounting, storeId));
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
