package com.sososhopping.repository.member;

import com.sososhopping.entity.member.AccountStatus;
import com.sososhopping.entity.member.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //이메일로 정보 찾기
    Optional<User> findByEmail(String email);

    //이메일 중복 확인
    boolean existsByEmail(String email);

    //핸드폰 번호로 read
    Optional<User> findByPhone(String phone);

    // 닉네임 중복 확인
    boolean existsByNickname(String nickname);

    // 핸드폰 중복 확인
    boolean existsByPhone(String phone);

    Optional<User> findByNameAndPhone(String name, String phone);

    Optional<User> findByEmailAndNameAndPhone(String email, String name, String phone);

    @EntityGraph(attributePaths = "orders", type = EntityGraph.EntityGraphType.FETCH)
    List<User> findByActive(AccountStatus status);
}
