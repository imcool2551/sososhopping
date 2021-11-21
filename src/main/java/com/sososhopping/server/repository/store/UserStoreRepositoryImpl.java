package com.sososhopping.server.repository.store;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.*;
import static com.querydsl.core.types.dsl.MathExpressions.*;
import static com.sososhopping.server.entity.store.QStore.store;


public class UserStoreRepositoryImpl implements UserStoreRepository {

    private final double distanceUnit = 111.045;

    private final JPAQueryFactory queryFactory;

    public UserStoreRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}
