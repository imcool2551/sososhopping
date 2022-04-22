package com.sososhopping.repository.order;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sososhopping.entity.member.Cart;
import com.sososhopping.entity.member.User;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sososhopping.entity.member.QCart.cart;
import static com.sososhopping.entity.store.QItem.item;

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
