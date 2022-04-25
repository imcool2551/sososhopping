package com.sososhopping.repository.report;

import com.sososhopping.entity.user.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {
}
