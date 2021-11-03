package com.sososhopping.server.repository.member;

import com.sososhopping.server.entity.member.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    //이메일로 정보 찾기
    Optional<Owner> findByEmail(String email);

    //이메일 중복 확인
    boolean existsByEmail(String email);
}
