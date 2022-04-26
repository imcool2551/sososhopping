package com.sososhopping.domain.store.controller;

import com.sososhopping.common.dto.ApiResponse;
import com.sososhopping.domain.store.dto.response.StoreItemDto;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.domain.store.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final StoreRepository storeRepository;

    @GetMapping("/store/{storeId}/item/{itemId}")
    public StoreItemDto findStoreItems(@PathVariable Long storeId, @PathVariable Long itemId) {
        return itemService.findStoreItem(storeId, itemId);
    }

    @GetMapping("/store/{storeId}/item")
    public ApiResponse findStoreItems(@PathVariable Long storeId) {
        return new ApiResponse(itemService.findStoreItems(storeId));
    }
}
