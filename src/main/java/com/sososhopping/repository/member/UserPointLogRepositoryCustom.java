package com.sososhopping.repository.member;

import com.sososhopping.entity.member.UserPoint;
import com.sososhopping.entity.member.UserPointLog;

import java.time.LocalDateTime;
import java.util.List;

public interface UserPointLogRepositoryCustom {
    List<UserPointLog> findMonthlyUserPointLogs(UserPoint userPoint, LocalDateTime at);
}
