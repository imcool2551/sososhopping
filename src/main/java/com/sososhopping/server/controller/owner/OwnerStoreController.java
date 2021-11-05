package com.sososhopping.server.controller.owner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sososhopping.server.common.dto.owner.request.StoreRequestDto;
import com.sososhopping.server.common.dto.owner.response.StoreListResponseDto;
import com.sososhopping.server.common.dto.owner.response.StoreResponseDto;
import com.sososhopping.server.common.error.Api500Exception;
import com.sososhopping.server.service.owner.OwnerStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OwnerStoreController {

    private final OwnerStoreService ownerStoreService;

    @GetMapping(value = "/api/v1/owner/store")
    public ResponseEntity readStoreList(Authentication authentication) {
        List<StoreListResponseDto> stores = ownerStoreService.readStoreList(
                Long.parseLong(authentication.getName()));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stores);
    }

    @PostMapping(value = "/api/v1/owner/store"
            ,consumes = { "multipart/form-data" }
    )
    public ResponseEntity createStore(Authentication authentication
            , @RequestPart(value = "data") String data
            , @RequestPart(value = "img", required = false) MultipartFile image) {
        StoreRequestDto dto;

        //string 으로 받은 data 다시 json화
        try {
            dto = new ObjectMapper().readValue(data, StoreRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new Api500Exception("데이터 변환에 실패했습니다");
        }

        ownerStoreService.createStore(dto, Long.parseLong(authentication.getName()), image);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value = "/api/v1/owner/store/{storeId}")
    public ResponseEntity readStore(Authentication authentication, @PathVariable(name = "storeId") Long storeId) {
        StoreResponseDto storeResponseDto = ownerStoreService.readStore(storeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(storeResponseDto);
    }

    @PatchMapping(value = "/api/v1/owner/store/{storeId}")
    public ResponseEntity updateStore(Authentication authentication
            , @PathVariable(name = "storeId") Long storeId
            , @RequestPart(value = "data") String data
            , @RequestPart(value = "img", required = false) MultipartFile image) {
        StoreRequestDto dto;

        //string 으로 받은 data 다시 json화
        try {
            dto = new ObjectMapper().readValue(data, StoreRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new Api500Exception("데이터 변환에 실패했습니다");
        }

        ownerStoreService.updateStore(storeId, dto, image);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/v1/owner/store/{storeId}")
    public ResponseEntity deleteStore(Authentication authentication
            , @PathVariable(name = "storeId") Long storeId) {
        ownerStoreService.deleteStore(storeId);

        return new ResponseEntity(HttpStatus.OK);
    }
}
