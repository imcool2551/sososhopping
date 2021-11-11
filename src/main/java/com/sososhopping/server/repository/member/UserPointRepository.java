package com.sososhopping.server.repository.member;

import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.member.UserPoint;
import com.sososhopping.server.entity.member.UserPointId;
import com.sososhopping.server.entity.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPointRepository extends JpaRepository<UserPoint, UserPointId> {
    Optional<UserPoint> findByUserAndStore(User user, Store store);
}
