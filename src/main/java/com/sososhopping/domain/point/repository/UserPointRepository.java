package com.sososhopping.domain.point.repository;

import com.sososhopping.entity.user.User;
import com.sososhopping.entity.point.UserPoint;
import com.sososhopping.entity.store.Store;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPointRepository extends JpaRepository<UserPoint, Long> {
    Optional<UserPoint> findByUserAndStore(User user, Store store);

    @EntityGraph(attributePaths = {"store"}, type = EntityGraph.EntityGraphType.FETCH)
    List<UserPoint> findByUser(User user);
}
