package com.sososhopping.server.controller.user.store;

import com.sososhopping.server.common.dto.ApiResponse;
import com.sososhopping.server.common.dto.user.response.store.ItemDto;
import com.sososhopping.server.common.error.Api404Exception;
import com.sososhopping.server.entity.store.Item;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.repository.store.ItemRepository;
import com.sososhopping.server.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class
UserItemController {

    private final StoreRepository storeRepository;

    @GetMapping("/api/v1/stores/{storeId}/items")
    public ApiResponse<ItemDto> getStoreItems(@PathVariable Long storeId) {

        Store findStore = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        List<ItemDto> itemDtoList = findStore.getItems()
                .stream()
                .map(item -> new ItemDto(item))
                .collect(Collectors.toList());

        return new ApiResponse<ItemDto>(itemDtoList);
    }
}
