package com.sososhopping.server.repository.order;

import com.sososhopping.server.entity.orders.OrderItem;
import com.sososhopping.server.entity.orders.OrderItemId;
import com.sososhopping.server.entity.store.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
    boolean existsByItem(Item item);
}
