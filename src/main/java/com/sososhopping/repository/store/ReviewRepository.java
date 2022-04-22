package com.sososhopping.repository.store;

import com.sososhopping.entity.member.Review;
import com.sososhopping.entity.member.ReviewId;
import com.sososhopping.entity.user.User;
import com.sososhopping.entity.store.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, ReviewId>, UserReviewRepository {

    boolean existsByUserAndStore(User user, Store store);

    Optional<Review> findByUserAndStore(User user, Store store);

    //점주 리뷰 with 유저
    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByStoreOrderByCreatedAtDesc(Store store);

    Slice<Review> findReviewsByStore(Store store, Pageable pageable);
}
