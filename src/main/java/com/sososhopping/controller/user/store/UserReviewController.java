package com.sososhopping.controller.user.store;

import com.sososhopping.common.dto.OffsetBasedPageRequest;
import com.sososhopping.common.dto.ApiListResponse;
import com.sososhopping.common.dto.user.request.store.ReviewCreateDto;
import com.sososhopping.common.dto.user.response.store.StoreReviewDto;
import com.sososhopping.common.dto.user.response.store.UserReviewDto;
import com.sososhopping.common.error.Api404Exception;
import com.sososhopping.common.error.Api409Exception;
import com.sososhopping.entity.store.Store;
import com.sososhopping.repository.store.ReviewRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.service.user.store.UserReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserReviewController {

    private final UserReviewService userReviewService;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;

    @GetMapping("/api/v1/users/stores/{storeId}/reviews")
    public ApiListResponse<StoreReviewDto> getStoreReviews(@PathVariable Long storeId) {

        List<StoreReviewDto> storeReviews = userReviewService.getStoreReviews(storeId);
        return new ApiListResponse<StoreReviewDto>(storeReviews);
    }

    @GetMapping("/api/v1/users/stores/{storeId}/reviews/page")
    public Slice<StoreReviewDto> getStoreReviewsPageable(
            @PathVariable Long storeId,
            @RequestParam Integer offset
    ) {
        Store findStore = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        Pageable pageable = new OffsetBasedPageRequest(offset, 10);

        return reviewRepository.findReviewsByStore(findStore, pageable)
                .map(StoreReviewDto::new);
    }

    @GetMapping("/api/v1/users/stores/{storeId}/reviews/check")
    public ResponseEntity checkStoreReview(
            Authentication authentication,
            @PathVariable Long storeId
    ) {
        Long userId = Long.parseLong(authentication.getName());
        boolean exists = userReviewService.existingReviewByUserAndStore(userId, storeId);

        if (exists) {
            throw new Api409Exception("이미 작성한 리뷰가 있습니다");
        }

        return ResponseEntity
                .status(200)
                .body(null);
    }

    @PostMapping("/api/v1/users/stores/{storeId}/reviews")
    public ResponseEntity createReview(
            Authentication authentication,
            @PathVariable Long storeId,
            @RequestBody @Valid ReviewCreateDto dto
    ) {

        Long userId = Long.parseLong(authentication.getName());
        userReviewService.createReview(userId, storeId, dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }

    @PutMapping("/api/v1/users/stores/{storeId}/reviews")
    public ResponseEntity updateMyReview(
            Authentication authentication,
            @PathVariable Long storeId,
            @RequestBody @Valid ReviewCreateDto dto
    ) {
        Long userId = Long.parseLong(authentication.getName());
        userReviewService.updateReview(userId, storeId, dto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @DeleteMapping("/api/v1/users/stores/{storeId}/reviews")
    public ResponseEntity deleteMyReview(
            Authentication authentication,
            @PathVariable Long storeId
    ) {
        Long userId = Long.parseLong(authentication.getName());
        userReviewService.deleteReview(userId, storeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @GetMapping("/api/v1/users/my/reviews")
    public ApiListResponse<UserReviewDto> getMyReviews(Authentication authentication) {

        Long userId = Long.parseLong(authentication.getName());

        List<UserReviewDto> dtos = reviewRepository.findReviewsByUserId(userId)
                .stream()
                .map(review -> new UserReviewDto(review))
                .collect(Collectors.toList());

        return new ApiListResponse<UserReviewDto>(dtos);
    }


}
