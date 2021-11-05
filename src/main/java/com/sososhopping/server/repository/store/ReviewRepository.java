package com.sososhopping.server.repository.store;

import com.sososhopping.server.entity.member.Review;
import com.sososhopping.server.entity.member.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, ReviewId>, UserReviewRepository {
}
