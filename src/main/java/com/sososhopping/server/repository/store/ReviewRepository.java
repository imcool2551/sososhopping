package com.sososhopping.server.repository.store;

import com.sososhopping.server.entity.member.Review;
import com.sososhopping.server.entity.member.ReviewId;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, ReviewId>, UserReviewRepository {

    boolean existsByUserAndStore(User user, Store store);
}
