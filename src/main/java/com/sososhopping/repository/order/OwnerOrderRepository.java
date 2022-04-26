package com.sososhopping.repository.order;

import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.store.Store;

import java.time.LocalDate;
import java.util.List;

public interface OwnerOrderRepository {

    List<Order> findPendingOrdersByStore(Store store);

    List<Order> findPickupOrdersByStore(Store store);

    List<Order> findDeliveryOrdersByStore(Store store);

    List<Order> findOrdersByStoreAndDate(Store store, LocalDate at);
}
