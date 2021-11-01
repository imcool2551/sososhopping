package com.sososhopping.server.repository.member;

import com.sososhopping.server.domain.entity.member.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    //닉네임 찾기
    Optional<Admin> findByNickname(String nickname);

    //닉네임 중복 방지
    boolean existsByNickname(String nickname);
}
