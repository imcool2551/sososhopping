package com.sososhopping.auth.repository;

import com.sososhopping.entity.member.AccountStatus;
import com.sososhopping.entity.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    boolean existsByPhone(String phone);

    Optional<User> findByPhone(String phone);

    Optional<User> findByNameAndPhone(String name, String phone);

    Optional<User> findByEmailAndNameAndPhone(String email, String name, String phone);

    @EntityGraph(attributePaths = "orders", type = EntityGraph.EntityGraphType.FETCH)
    List<User> findByActive(AccountStatus status);
}
