package com.sososhopping.controller.user.store;

import com.sososhopping.common.dto.ApiListResponse;
import com.sososhopping.common.dto.user.response.store.ItemDto;
import com.sososhopping.common.error.Api404Exception;
import com.sososhopping.entity.owner.Store;
import com.sososhopping.domain.store.repository.StoreRepository;
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

    @GetMapping("/api/v1/users/stores/{storeId}/items")
    public ApiListResponse<ItemDto> getStoreItems(@PathVariable Long storeId) {

        Store findStore = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        List<ItemDto> itemDtoList = findStore.getItems()
                .stream()
                .map(item -> new ItemDto(item))
                .collect(Collectors.toList());

        return new ApiListResponse<ItemDto>(itemDtoList);
    }
}
