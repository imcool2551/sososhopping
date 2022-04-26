package com.sososhopping.repository.member;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sososhopping.entity.user.UserPoint;
import com.sososhopping.entity.user.UserPointLog;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.sososhopping.entity.user.QUserPointLog.userPointLog;


public class UserPointLogRepositoryCustomImpl implements UserPointLogRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserPointLogRepositoryCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<UserPointLog> findMonthlyUserPointLogs(
            UserPoint userPoint, LocalDateTime at
    ) {
        return queryFactory
                .select(userPointLog)
                .from(userPointLog)
                .where(userPointEq(userPoint), monthEq(at))
                .orderBy(userPointLog.createdAt.asc())
                .fetch();
    }

    private BooleanExpression userPointEq(UserPoint userPoint) {
        return userPointLog.userPoint.eq(userPoint);
    }

    private BooleanExpression monthEq(LocalDateTime at) {
        LocalDateTime startOfMonth = LocalDateTime.of(at.getYear(), at.getMonth(), 1, 00, 00, 00);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusMinutes(1);

        return userPointLog.createdAt.between(
                startOfMonth,
                endOfMonth
        );
    }
}