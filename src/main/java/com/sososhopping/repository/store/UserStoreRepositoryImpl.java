package com.sososhopping.repository.store;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;


public class UserStoreRepositoryImpl implements UserStoreRepository {

    private final double distanceUnit = 111.045;

    private final JPAQueryFactory queryFactory;

    public UserStoreRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}
