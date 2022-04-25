package com.sososhopping.repository.store;

import com.sososhopping.entity.user.Review;

import java.util.List;

public interface UserReviewRepository {

    List<Review> findReviewsByStoreIdOrderByCreatedAtDesc(Long storeId);
    List<Review> findReviewsByUserId(Long userId);
}
