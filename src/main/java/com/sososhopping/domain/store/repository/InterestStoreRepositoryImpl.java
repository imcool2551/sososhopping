package com.sososhopping.domain.store.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sososhopping.entity.user.InterestStore;
import com.sososhopping.entity.user.User;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sososhopping.entity.store.QStore.store;
import static com.sososhopping.entity.user.QInterestStore.interestStore;


public class InterestStoreRepositoryImpl implements InterestStoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public InterestStoreRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<InterestStore> findAllByUserId(Long userId) {
        return queryFactory
                .select(interestStore)
                .from(interestStore)
                .join(interestStore.store, store).fetchJoin()
                .where(userIdEq(userId))
                .fetch();
    }

    @Override
    public List<InterestStore> findInterestStoresByUser(User user) {
        return queryFactory
                .select(interestStore)
                .from(interestStore)
                .join(interestStore.store, store).fetchJoin()
                .where(userEq(user))
                .fetch();
    }

    private BooleanExpression userEq(User user) {
        if (user == null) {
            return null;
        }
        return interestStore.user.eq(user);
    }

    private BooleanExpression userIdEq(Long userId) {
        if (userId == null) {
            return null;
        }
        return interestStore.user.id.eq(userId);
    }
}
