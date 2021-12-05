package com.sososhopping.server.repository.order;

import com.sososhopping.server.entity.orders.Order;
import com.sososhopping.server.entity.store.Store;

import java.time.LocalDate;
import java.util.List;

public interface OwnerOrderRepository {

    List<Order> findPendingOrdersByStore(Store store);

    List<Order> findPickupOrdersByStore(Store store);

    List<Order> findDeliveryOrdersByStore(Store store);

    List<Order> findOrdersByStoreAndDate(Store store, LocalDate at);
}
