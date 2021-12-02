package com.sososhopping.server.repository.store;

import com.sososhopping.server.entity.member.Review;
import com.sososhopping.server.entity.store.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface UserReviewRepository {

    List<Review> findReviewsByStoreId(Long storeId);
    List<Review> findReviewsByUserId(Long userId);
}
