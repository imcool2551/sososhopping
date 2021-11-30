package com.sososhopping.server.repository.order;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.orders.Order;
import com.sososhopping.server.entity.orders.OrderStatus;
import com.sososhopping.server.entity.orders.QOrderItem;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sososhopping.server.entity.orders.QOrder.*;
import static com.sososhopping.server.entity.orders.QOrderItem.*;
import static com.sososhopping.server.entity.store.QStore.*;

public class UserOrderRepositoryImpl implements UserOrderRepository {

    private final JPAQueryFactory queryFactory;

    public UserOrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<Order> findOrderListByUserAndOrderStatus(User user, OrderStatus ... status) {

        BooleanExpression orderStatusMatch = orderStatusEq(status[0]);
        for (int i = 1; i < status.length; i++) {
            orderStatusMatch = orderStatusMatch.or(orderStatusEq(status[i]));
        }

        return queryFactory
                .select(order)
                .from(order)
                .join(order.store, store).fetchJoin()
                .where(userEq(user), orderStatusMatch)
                .orderBy(order.createdAt.desc())
                .fetch();
    }

    private BooleanExpression userEq(User user) {
        return order.user.eq(user);
    }

    private BooleanExpression orderStatusEq(OrderStatus status) {
        return order.orderStatus.eq(status);
    }
}
