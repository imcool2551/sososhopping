package com.sososhopping.controller.owner;

import com.sososhopping.common.dto.owner.request.StoreItemRequestDto;
import com.sososhopping.common.dto.owner.response.StoreItemResponseDto;
import com.sososhopping.entity.orders.Item;
import com.sososhopping.service.owner.StoreItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class StoreItemController {
    private final StoreItemService storeItemService;

    @GetMapping(value = "/api/v1/owner/store/{storeId}/item")
    public ResponseEntity readItemList(
            @PathVariable(value = "storeId") Long storeId) {
        List<StoreItemResponseDto> items
                = storeItemService.readItemList(storeId)
                .stream()
                .map(item -> new StoreItemResponseDto(item, storeId))
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(items);
    }

    @GetMapping(value = "/api/v1/owner/store/{storeId}/item/{itemId}")
    public ResponseEntity readItem(
            @PathVariable(value = "storeId") Long storeId
            , @PathVariable(value = "itemId") Long itemId) {
        Item item = storeItemService.readItem(storeId, itemId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new StoreItemResponseDto(item, storeId));
    }

    @PostMapping(value = "/api/v1/owner/store/{storeId}/item")
    public ResponseEntity createItem(
            @PathVariable(value = "storeId") Long storeId
            , @RequestPart(value = "dto") StoreItemRequestDto dto
            , @RequestPart(value = "img", required = false) MultipartFile image) {
        storeItemService.createItem(storeId, dto, image);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PatchMapping(value = "/api/v1/owner/store/{storeId}/item/{itemId}")
    public ResponseEntity updateItem(
            @PathVariable(value = "storeId") Long storeId
            , @PathVariable(value = "itemId") Long itemId
            , @RequestPart(value = "dto") StoreItemRequestDto dto
            , @RequestPart(value = "img", required = false) MultipartFile image) {
        storeItemService.updateItem(storeId, itemId, dto, image);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/v1/owner/store/{storeId}/item/{itemId}")
    public ResponseEntity deleteItem(
            @PathVariable(value = "storeId") Long storeId
            , @PathVariable(value = "itemId") Long itemId) {
        storeItemService.deleteItem(storeId, itemId);

        return new ResponseEntity(HttpStatus.OK);
    }
}
