package com.sososhopping.repository.member;

import com.sososhopping.entity.user.User;
import com.sososhopping.entity.member.UserPoint;
import com.sososhopping.entity.member.UserPointId;
import com.sososhopping.entity.store.Store;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPointRepository extends JpaRepository<UserPoint, UserPointId> {
    Optional<UserPoint> findByUserAndStore(User user, Store store);

    @EntityGraph(attributePaths = {"store"}, type = EntityGraph.EntityGraphType.FETCH)
    List<UserPoint> findByUser(User user);
}
