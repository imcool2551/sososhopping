package com.sososhopping.server.repository.member;

import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.member.UserPoint;
import com.sososhopping.server.entity.member.UserPointLog;
import com.sososhopping.server.entity.store.Store;

import java.time.LocalDateTime;
import java.util.List;

public interface UserPointLogRepositoryCustom {
    List<UserPointLog> findMonthlyUserPointLogs(UserPoint userPoint, LocalDateTime at);
}
