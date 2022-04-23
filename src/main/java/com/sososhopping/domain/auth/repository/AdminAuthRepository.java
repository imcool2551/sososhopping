package com.sososhopping.domain.auth.repository;

import com.sososhopping.entity.member.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminAuthRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByNickname(String nickname);
}
