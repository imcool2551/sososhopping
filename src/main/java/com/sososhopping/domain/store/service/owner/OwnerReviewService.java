package com.sososhopping.domain.store.service.owner;

import com.sososhopping.domain.owner.service.OwnerValidationService;
import com.sososhopping.domain.store.dto.owner.response.StoreReviewsResponse;
import com.sososhopping.domain.store.dto.user.response.StoreReviewResponse;
import com.sososhopping.domain.store.repository.ReviewRepository;
import com.sososhopping.entity.store.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OwnerReviewService {

    private final OwnerValidationService ownerValidationService;
    private final ReviewRepository reviewRepository;

    public StoreReviewsResponse findStoreReviews(Long ownerId, Long storeId) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);
        List<StoreReviewResponse> reviews = reviewRepository.findByStoreOrderByCreatedAtDesc(store)
                .stream()
                .map(StoreReviewResponse::new)
                .collect(Collectors.toList());

        double averageScore = reviews.stream()
                .mapToDouble(review -> review.getScore().doubleValue())
                .average()
                .orElse(0);

        return new StoreReviewsResponse(reviews.size(), averageScore, reviews);
    }
}
