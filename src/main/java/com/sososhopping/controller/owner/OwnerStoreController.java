package com.sososhopping.controller.owner;

import com.sososhopping.common.dto.owner.response.StoreBusinessStatusResponseDto;
import com.sososhopping.common.dto.owner.response.StoreResponseDto;
import com.sososhopping.entity.owner.Store;
import com.sososhopping.service.owner.OwnerStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OwnerStoreController {

    private final OwnerStoreService ownerStoreService;

    @GetMapping(value = "/api/v1/owner/store/{storeId}")
    public ResponseEntity readStore(Authentication authentication, @PathVariable(name = "storeId") Long storeId) {
        Store store = ownerStoreService.readStore(storeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new StoreResponseDto(store));
    }


    @GetMapping(value = "/api/v1/owner/store/{storeId}/businessstatus")
    public ResponseEntity readStoreBusinessStatus(@PathVariable(name = "storeId") Long storeId) {
        Boolean businessStatus = ownerStoreService.readStoreBusinessStatus(storeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new StoreBusinessStatusResponseDto(businessStatus));
    }

    @PatchMapping(value = "/api/v1/owner/store/{storeId}/businessstatus")
    public ResponseEntity updateStoreBusinessStatus(@PathVariable(name = "storeId") Long storeId) {
        Boolean businessStatus = ownerStoreService.updateStoreBusinessStatus(storeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new StoreBusinessStatusResponseDto(businessStatus));
    }
}
