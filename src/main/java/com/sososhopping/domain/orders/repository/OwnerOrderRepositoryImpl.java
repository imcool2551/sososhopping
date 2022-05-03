package com.sososhopping.domain.orders.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.orders.OrderStatus;
import com.sososhopping.entity.orders.OrderType;
import com.sososhopping.entity.store.Store;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.sososhopping.entity.orders.OrderStatus.*;
import static com.sososhopping.entity.orders.OrderType.DELIVERY;
import static com.sososhopping.entity.orders.OrderType.ONSITE;
import static com.sososhopping.entity.orders.QOrder.order;

public class OwnerOrderRepositoryImpl implements OwnerOrderRepository {

    private final JPAQueryFactory queryFactory;

    public OwnerOrderRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Order> findPendingOrders(Store store) {
        return queryFactory
                .select(order)
                .from(order)
                .where(storeEq(store), statusEq(PENDING))
                .orderBy(order.createdAt.asc())
                .fetch();
    }

    @Override
    public List<Order> findTodayPickupOrders(Store store, LocalDate at) {
        return queryFactory
                .select(order)
                .from(order)
                .where(storeEq(store), statusEq(APPROVE).or(statusEq(READY)), pickupDateEq(at))
                .orderBy(order.visitDate.asc())
                .fetch();
    }

    @Override
    public List<Order> findTodayDeliveryOrders(Store store, LocalDate at) {
        return queryFactory
                .select(order)
                .from(order)
                .where(storeEq(store), statusEq(APPROVE), deliveryDateEq(at))
                .orderBy(order.createdAt.asc())
                .fetch();
    }

    @Override
    public List<Order> findProcessedOrders(Store store, LocalDate date) {
        return queryFactory
                .select(order)
                .from(order)
                .where(storeEq(store), pickupDateEq(date).or(deliveryDateEq(date)), order.orderStatus.notIn(PENDING))
                .orderBy(order.createdAt.asc())
                .fetch();
    }

    private BooleanExpression storeEq(Store store) {
        return order.store.eq(store);
    }

    private BooleanExpression statusEq(OrderStatus status) {
        return order.orderStatus.eq(status);
    }

    private BooleanExpression pickupDateEq(LocalDate date) {
        if (date == null) {
            return null;
        }

        return orderTypeEq(ONSITE)
                .and(order.visitDate.between(
                        date.atStartOfDay(),
                        date.atStartOfDay().plusDays(1).minusMinutes(1)));
    }

    private BooleanExpression deliveryDateEq(LocalDate date) {
        if (date == null) {
            return null;
        }

        return orderTypeEq(DELIVERY)
                .and(order.createdAt.between(
                        date.atStartOfDay(),
                        date.atStartOfDay().plusDays(1).minusMinutes(1)));
    }

    private BooleanExpression orderTypeEq(OrderType type) {
        return order.orderType.eq(type);
    }
}
