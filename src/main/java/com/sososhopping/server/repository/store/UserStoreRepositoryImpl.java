package com.sososhopping.server.repository.store;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;


public class UserStoreRepositoryImpl implements UserStoreRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public UserStoreRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }
}
