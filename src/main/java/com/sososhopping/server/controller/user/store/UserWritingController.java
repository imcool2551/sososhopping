package com.sososhopping.server.controller.user.store;

import com.sososhopping.server.common.dto.ApiResponse;
import com.sososhopping.server.common.dto.user.response.store.WritingDto;
import com.sososhopping.server.common.dto.user.response.store.WritingListDto;
import com.sososhopping.server.common.error.Api404Exception;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.entity.store.Writing;
import com.sososhopping.server.repository.store.StoreRepository;
import com.sososhopping.server.service.user.store.UserWritingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserWritingController {

    private final UserWritingService userWritingService;
    private final StoreRepository storeRepository;

    @GetMapping("/api/v1/users/stores/{storeId}/writings")
    public ApiResponse<WritingListDto> getStoreWritings(@PathVariable Long storeId) {

        Store findStore = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        List<WritingListDto> writingListDto = findStore.getWritings()
                .stream()
                .map(writing -> new WritingListDto(writing))
                .collect(Collectors.toList());

        return new ApiResponse<WritingListDto>(writingListDto);
    }

    @GetMapping("/api/v1/users/stores/{storeId}/writings/{writingId}")
    public WritingDto getStoreWriting(
            @PathVariable Long storeId,
            @PathVariable Long writingId
    ) {
        Writing writing = userWritingService.getWriting(storeId, writingId);
        return new WritingDto(writing);
    }
}
