package com.sososhopping.domain.store.repository;

import com.sososhopping.entity.user.Review;
import com.sososhopping.entity.user.User;
import com.sososhopping.entity.store.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.*;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByUser(User user);

    boolean existsByUserAndStore(User user, Store store);

    Optional<Review> findByUserAndStore(User user, Store store);

    @EntityGraph(attributePaths = {"user"}, type = FETCH)
    Slice<Review> findByStoreOrderByCreatedAtDesc(Store store, Pageable pageable);

    @EntityGraph(attributePaths = {"user"}, type = FETCH)
    List<Review> findByStoreOrderByCreatedAtDesc(Store store);
}
