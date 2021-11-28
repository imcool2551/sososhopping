package com.sososhopping.server.repository.order;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sososhopping.server.entity.orders.Order;
import com.sososhopping.server.entity.orders.OrderStatus;
import com.sososhopping.server.entity.store.Store;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.sososhopping.server.entity.orders.OrderType.*;
import static com.sososhopping.server.entity.orders.QOrder.*;

public class OwnerOrderRepositoryImpl implements OwnerOrderRepository {

    private final JPAQueryFactory queryFactory;

    public OwnerOrderRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Order> findPendingOrdersByStore(Store store) {
        return queryFactory
                .select(order)
                .from(order)
                .where(storeEq(store), pending())
                .orderBy(order.createdAt.asc())
                .fetch();
    }

    @Override
    public List<Order> findOrdersByStoreAndDate(Store store, LocalDate date) {
        return queryFactory
                .select(order)
                .from(order)
                .where(storeEq(store), dateEq(date))
                .fetch();
    }

    private BooleanExpression pending() {
        return order.orderStatus.eq(OrderStatus.PENDING);
    }

    private BooleanExpression storeEq(Store store) {
        return order.store.eq(store);
    }

    private BooleanExpression dateEq(LocalDate date) {
        BooleanExpression todayVisit = order.orderType.eq(ONSITE)
                .and(
                        order.visitDate.between(
                                date.atStartOfDay(),
                                date.atStartOfDay().plusDays(1)
                        )
                );

        BooleanExpression todayDelivery = order.orderType.eq(DELIVERY)
                .and(
                        order.createdAt.between(
                                date.atStartOfDay(),
                                date.atStartOfDay().plusDays(1)
                        )
                );

        return todayVisit.and(todayDelivery);
    }
}
