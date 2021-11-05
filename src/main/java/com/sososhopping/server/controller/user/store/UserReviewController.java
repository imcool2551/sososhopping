package com.sososhopping.server.controller.user.store;

import com.sososhopping.server.common.dto.ApiResponse;
import com.sososhopping.server.common.dto.user.response.store.ReviewDto;
import com.sososhopping.server.common.error.Api404Exception;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.repository.store.ReviewRepository;
import com.sososhopping.server.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserReviewController {

    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;

    @GetMapping("/api/v1/stores/{storeId}/reviews")
    public ApiResponse<ReviewDto> getStoreReviews(@PathVariable Long storeId) {
        Store findStore = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        List<ReviewDto> reviewDtoList = reviewRepository.findReviewsByStore(storeId)
                .stream()
                .map(review -> new ReviewDto(review))
                .collect(Collectors.toList());

        return new ApiResponse<ReviewDto>(reviewDtoList);
    }
}
