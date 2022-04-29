package com.sososhopping.domain.store.controller.owner;

import com.sososhopping.common.dto.ApiResponse;
import com.sososhopping.domain.store.dto.owner.request.CreateItemDto;
import com.sososhopping.domain.store.dto.owner.response.StoreItemResponse;
import com.sososhopping.domain.store.service.owner.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/owner/my/store/{storeId}/item")
    public ApiResponse createItem(Authentication authentication,
                                  @PathVariable Long storeId,
                                  @RequestBody @Valid CreateItemDto dto) {

        Long ownerId = Long.parseLong(authentication.getName());
        return new ApiResponse(itemService.createItem(ownerId, storeId, dto));
    }

    @GetMapping("/store/{storeId}/item/{itemId}")
    public StoreItemResponse findStoreItems(@PathVariable Long storeId, @PathVariable Long itemId) {
        return itemService.findStoreItem(storeId, itemId);
    }

    @GetMapping("/store/{storeId}/item")
    public ApiResponse findStoreItems(@PathVariable Long storeId) {
        return new ApiResponse(itemService.findStoreItems(storeId));
    }
}
