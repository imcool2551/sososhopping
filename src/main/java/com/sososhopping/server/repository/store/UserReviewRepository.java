package com.sososhopping.server.repository.store;

import com.sososhopping.server.entity.member.Review;

import java.util.List;

public interface UserReviewRepository {

    List<Review> findReviewsByStore(Long storeId);
}
