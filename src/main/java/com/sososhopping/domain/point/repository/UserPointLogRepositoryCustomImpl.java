package com.sososhopping.domain.point.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sososhopping.entity.point.UserPoint;
import com.sososhopping.entity.point.UserPointLog;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.sososhopping.entity.point.QUserPointLog.userPointLog;
import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;


public class UserPointLogRepositoryCustomImpl implements UserPointLogRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserPointLogRepositoryCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<UserPointLog> findMonthlyUserPointLogs(UserPoint userPoint, LocalDate yearMonth) {
        return queryFactory
                .select(userPointLog)
                .from(userPointLog)
                .where(userPointEq(userPoint), monthEq(yearMonth))
                .orderBy(userPointLog.createdAt.asc())
                .fetch();
    }

    private BooleanExpression userPointEq(UserPoint userPoint) {
        if (userPoint == null) {
            return null;
        }
        return userPointLog.userPoint.eq(userPoint);
    }

    private BooleanExpression monthEq(LocalDate yearMonth) {
        if (yearMonth == null) {
            return null;
        }
        LocalDate start = yearMonth.with(firstDayOfMonth());
        LocalDate end = yearMonth.with(lastDayOfMonth()).plusDays(1);
        return userPointLog.createdAt.between(start.atStartOfDay(), end.atStartOfDay());
    }
}
