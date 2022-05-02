package com.sososhopping.domain.store.controller.user;

import com.sososhopping.common.dto.ApiResponse;
import com.sososhopping.common.dto.OffsetBasedPageRequest;
import com.sososhopping.domain.store.dto.user.request.CreateReviewDto;
import com.sososhopping.domain.store.dto.user.response.StoreReviewResponse;
import com.sososhopping.domain.store.service.user.UserReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserReviewController {

    private final UserReviewService userReviewService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/store/{storeId}/reviews")
    public void createReview(Authentication authentication,
                             @PathVariable Long storeId,
                             @RequestBody @Valid CreateReviewDto dto) {

        Long userId = Long.parseLong(authentication.getName());
        userReviewService.createReview(userId, storeId, dto);
    }


    @GetMapping("/store/{storeId}/reviews")
    public Slice<StoreReviewResponse> findStoreReviews(@PathVariable Long storeId,
                                                       @RequestParam Integer offset,
                                                       @RequestParam Integer limit) {

        Pageable pageable = new OffsetBasedPageRequest(offset, limit);
        return userReviewService.findStoreReviews(storeId, pageable);
    }

    @GetMapping("/users/my/reviews")
    public ApiResponse findMyReviews(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        List<StoreReviewResponse> reviews = userReviewService.findMyReviews(userId);
        return new ApiResponse(reviews);
    }

    @GetMapping("/users/my/reviews/store/{storeId}")
    public ApiResponse checkReview(Authentication authentication, @PathVariable Long storeId) {
        Long userId = Long.parseLong(authentication.getName());
        return new ApiResponse(userReviewService.checkReview(userId, storeId));
    }


    @DeleteMapping("/users/my/reviews/store/{storeId}")
    public void deleteReview(Authentication authentication, @PathVariable Long storeId) {
        Long userId = Long.parseLong(authentication.getName());
        userReviewService.deleteReview(userId, storeId);
    }


}
