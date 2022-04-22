package com.sososhopping.controller.user.store;

import com.sososhopping.common.OffsetBasedPageRequest;
import com.sososhopping.common.dto.ApiListResponse;
import com.sososhopping.common.dto.user.response.store.WritingDto;
import com.sososhopping.common.dto.user.response.store.WritingListDto;
import com.sososhopping.common.error.Api404Exception;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.store.Writing;
import com.sososhopping.repository.store.StoreRepository;
import com.sososhopping.repository.store.WritingRepository;
import com.sososhopping.service.user.store.UserWritingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserWritingController {

    private final UserWritingService userWritingService;
    private final StoreRepository storeRepository;
    private final WritingRepository writingRepository;

    @GetMapping("/api/v1/users/stores/{storeId}/writings")
    public ApiListResponse<WritingListDto> getStoreWritings(@PathVariable Long storeId) {

        Store findStore = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        List<WritingListDto> writingListDto = findStore.getWritings()
                .stream()
                .map(writing -> new WritingListDto(writing))
                .collect(Collectors.toList());

        return new ApiListResponse<WritingListDto>(writingListDto);
    }

    @GetMapping("/api/v1/users/stores/{storeId}/writings/page")
    public Slice<WritingListDto> getStoreWritingsPageable(
            @PathVariable Long storeId,
            @RequestParam Integer offset
            ) {

        Store findStore = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));


        Pageable pageable = new OffsetBasedPageRequest(offset, 10);
        return writingRepository.findByStoreOrderByCreatedAtDesc(findStore, pageable)
                .map(WritingListDto::new);
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
