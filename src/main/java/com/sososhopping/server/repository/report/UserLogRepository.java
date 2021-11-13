package com.sososhopping.server.repository.report;

import com.sososhopping.server.entity.member.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {
}
