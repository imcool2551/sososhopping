package com.sososhopping.repository.member;

import com.sososhopping.entity.point.UserPointLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointLogRepository extends JpaRepository<UserPointLog, Long>, UserPointLogRepositoryCustom {

}
