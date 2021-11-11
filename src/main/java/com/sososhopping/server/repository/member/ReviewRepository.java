package com.sososhopping.server.repository.member;

import com.sososhopping.server.entity.member.Review;
import com.sososhopping.server.entity.member.ReviewId;
import com.sososhopping.server.entity.store.Store;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, ReviewId> {

    //점주 리뷰 with 유저
    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByStoreOrderByCreatedAtDesc(Store store);

}
