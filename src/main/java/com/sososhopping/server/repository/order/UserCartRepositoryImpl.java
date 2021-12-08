package com.sososhopping.server.repository.order;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sososhopping.server.entity.member.Cart;
import com.sososhopping.server.entity.member.User;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sososhopping.server.entity.member.QCart.*;
import static com.sososhopping.server.entity.store.QItem.*;
import static com.sososhopping.server.entity.store.QStore.*;

public class UserCartRepositoryImpl implements UserCartRepository {

    private final JPAQueryFactory queryFactory;

    public UserCartRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<Cart> getCartByUser(User user) {
        return queryFactory
                .select(cart)
                .from(cart)
                .join(cart.item, item).fetchJoin()
                .where(userEq(user))
                .fetch();
    }

    private BooleanExpression userEq(User user) {
        return cart.user.eq(user);
    }
}
