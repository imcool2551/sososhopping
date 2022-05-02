package com.sososhopping.domain.store.controller.owner;

import com.sososhopping.domain.store.dto.owner.response.StoreReviewsResponse;
import com.sososhopping.domain.store.service.owner.OwnerReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OwnerReviewController {

    private final OwnerReviewService ownerReviewService;

    @GetMapping("/owner/my/store/{storeId}/reviews")
    public StoreReviewsResponse findStoreReviews(Authentication authentication,
                                                 @PathVariable Long storeId) {

        Long ownerId = Long.parseLong(authentication.getName());
        return ownerReviewService.findStoreReviews(ownerId, storeId);
    }

}
