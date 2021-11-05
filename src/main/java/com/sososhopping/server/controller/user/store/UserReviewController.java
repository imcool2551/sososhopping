package com.sososhopping.server.controller.user.store;

import com.sososhopping.server.common.dto.ApiResponse;
import com.sososhopping.server.common.dto.user.request.store.ReviewCreateDto;
import com.sososhopping.server.common.dto.user.response.store.ReviewDto;
import com.sososhopping.server.common.error.Api401Exception;
import com.sososhopping.server.common.error.Api404Exception;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.repository.member.UserRepository;
import com.sososhopping.server.repository.store.ReviewRepository;
import com.sososhopping.server.repository.store.StoreRepository;
import com.sososhopping.server.service.user.store.UserReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserReviewController {

    private final UserReviewService userReviewService;

    @GetMapping("/api/v1/stores/{storeId}/reviews")
    public ApiResponse<ReviewDto> getStoreReviews(@PathVariable Long storeId) {

        List<ReviewDto> storeReviews = userReviewService.getStoreReviews(storeId);
        return new ApiResponse<ReviewDto>(storeReviews);
    }

    @PostMapping("/api/v1/stores/{storeId}/reviews")
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
}
