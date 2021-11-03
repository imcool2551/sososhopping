package com.sososhopping.server.repository.store;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sososhopping.server.entity.store.Store;

import javax.persistence.EntityManager;

import java.util.Optional;

public class UserStoreRepositoryImpl implements UserStoreRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public UserStoreRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }
}
