package com.sososhopping.server.repository.member;

import com.sososhopping.server.entity.member.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
