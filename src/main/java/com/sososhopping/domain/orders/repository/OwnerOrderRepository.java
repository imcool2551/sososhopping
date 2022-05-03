package com.sososhopping.domain.orders.repository;

import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.store.Store;

import java.time.LocalDate;
import java.util.List;

public interface OwnerOrderRepository {

    List<Order> findPendingOrders(Store store);

    List<Order> findTodayPickupOrders(Store store, LocalDate at);

    List<Order> findTodayDeliveryOrders(Store store, LocalDate at);

    List<Order> findProcessedOrders(Store store, LocalDate at);
}
