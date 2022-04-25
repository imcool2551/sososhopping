package com.sososhopping.repository.member;

import com.sososhopping.entity.user.UserPoint;
import com.sososhopping.entity.user.UserPointLog;

import java.time.LocalDateTime;
import java.util.List;

public interface UserPointLogRepositoryCustom {
    List<UserPointLog> findMonthlyUserPointLogs(UserPoint userPoint, LocalDateTime at);
}
