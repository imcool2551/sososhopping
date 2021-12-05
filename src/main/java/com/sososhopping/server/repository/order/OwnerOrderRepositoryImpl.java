package com.sososhopping.server.repository.order;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sososhopping.server.entity.orders.Order;
import com.sososhopping.server.entity.orders.OrderStatus;
import com.sososhopping.server.entity.orders.OrderType;
import com.sososhopping.server.entity.store.Store;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.sososhopping.server.entity.orders.OrderStatus.*;
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
                .where(storeEq(store), statusEq(PENDING))
                .orderBy(order.createdAt.asc())
                .fetch();
    }

    @Override
    public List<Order> findPickupOrdersByStore(Store store) {
        return queryFactory
                .select(order)
                .from(order)
                .where(
                        storeEq(store),
                        orderTypeEq(ONSITE),
                        statusEq(APPROVE)
                )
                .orderBy(order.visitDate.asc())
                .fetch();
    }


    @Override
    public List<Order> findDeliveryOrdersByStore(Store store) {
        return queryFactory
                .select(order)
                .from(order)
                .where(
                        storeEq(store),
                        orderTypeEq(DELIVERY),
                        statusEq(APPROVE)
                )
                .orderBy(order.createdAt.asc())
                .fetch();
    }

    @Override
    public List<Order> findDoneOrdersByStoreAndDate(Store store, LocalDate date) {
        return queryFactory
                .select(order)
                .from(order)
                .where(
                        storeEq(store),
                        dateEq(date),
                        statusEq(DONE)
                )
                .orderBy(order.createdAt.asc())
                .fetch();
    }

    private BooleanExpression storeEq(Store store) {
        return order.store.eq(store);
    }

    private BooleanExpression statusEq(OrderStatus status) {
        return order.orderStatus.eq(status);
    }

    private BooleanExpression orderTypeEq(OrderType type) {
        return order.orderType.eq(type);
    }

    private BooleanExpression dateEq(LocalDate date) {
        BooleanExpression visitDateEq = orderTypeEq(ONSITE)
                .and(
                        order.visitDate.between(
                                date.atStartOfDay(),
                                date.atStartOfDay().plusDays(1)
                        )
                );

        BooleanExpression deliveryDateEq = orderTypeEq(DELIVERY)
                .and(
                        order.createdAt.between(
                                date.atStartOfDay(),
                                date.atStartOfDay().plusDays(1)
                        )
                );

        return visitDateEq.and(deliveryDateEq);
    }
}
