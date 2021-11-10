package com.sososhopping.server.repository.store;

import com.sososhopping.server.entity.member.Review;

import java.util.List;

public interface UserReviewRepository {

    List<Review> findReviewsByStoreId(Long storeId);
    List<Review> findReviewsByUserId(Long userId);
}
