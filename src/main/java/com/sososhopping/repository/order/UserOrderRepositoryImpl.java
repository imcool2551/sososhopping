package com.sososhopping.repository.order;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sososhopping.entity.user.User;
import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.orders.OrderStatus;
import org.springframework.data.domain.*;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sososhopping.entity.orders.QOrder.order;
import static com.sososhopping.entity.store.QStore.store;

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

    @Override
    public Slice<Order> findOrdersByUserAndOrderStatus(User user, Pageable pageable, OrderStatus... status) {
        BooleanExpression orderStatusMatch = orderStatusEq(status[0]);
        for (int i = 1; i < status.length; i++) {
            orderStatusMatch = orderStatusMatch.or(orderStatusEq(status[i]));
        }

        List<Order> content = queryFactory
                .select(order)
                .from(order)
                .join(order.store, store).fetchJoin()
                .where(userEq(user), orderStatusMatch)
                .orderBy(order.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression userEq(User user) {
        return order.user.eq(user);
    }

    private BooleanExpression orderStatusEq(OrderStatus status) {
        return order.orderStatus.eq(status);
    }
}
