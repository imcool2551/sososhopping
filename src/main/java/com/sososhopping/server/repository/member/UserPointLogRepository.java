package com.sososhopping.server.repository.member;

import com.sososhopping.server.entity.member.UserPointLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointLogRepository extends JpaRepository<UserPointLog, Long>, UserPointLogRepositoryCustom {

}
