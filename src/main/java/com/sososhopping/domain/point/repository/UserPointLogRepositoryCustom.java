package com.sososhopping.domain.point.repository;

import com.sososhopping.entity.point.UserPoint;
import com.sososhopping.entity.point.UserPointLog;

import java.time.LocalDateTime;
import java.util.List;

public interface UserPointLogRepositoryCustom {
    List<UserPointLog> findMonthlyUserPointLogs(UserPoint userPoint, LocalDateTime at);
}
