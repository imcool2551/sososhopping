package com.sososhopping.server.controller.user.store;

import com.sososhopping.server.common.dto.ApiResponse;
import com.sososhopping.server.common.dto.user.request.store.ReviewCreateDto;
import com.sososhopping.server.common.dto.user.response.store.StoreReviewDto;
import com.sososhopping.server.common.dto.user.response.store.UserReviewDto;
import com.sososhopping.server.entity.member.Review;
import com.sososhopping.server.repository.store.ReviewRepository;
import com.sososhopping.server.service.user.store.UserReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final ReviewRepository reviewRepository;

    @GetMapping("/api/v1/users/stores/{storeId}/reviews")
    public ApiResponse<StoreReviewDto> getStoreReviews(@PathVariable Long storeId) {

        List<StoreReviewDto> storeReviews = userReviewService.getStoreReviews(storeId);
        return new ApiResponse<StoreReviewDto>(storeReviews);
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
    public ApiResponse<UserReviewDto> getMyReviews(Authentication authentication) {

        Long userId = Long.parseLong(authentication.getName());

        List<UserReviewDto> dtos = reviewRepository.findReviewsByUserId(userId)
                .stream()
                .map(review -> new UserReviewDto(review))
                .collect(Collectors.toList());

        return new ApiResponse<UserReviewDto>(dtos);
    }


}
