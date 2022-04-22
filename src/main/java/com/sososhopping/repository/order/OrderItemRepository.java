package com.sososhopping.repository.order;

import com.sososhopping.entity.orders.OrderItem;
import com.sososhopping.entity.orders.OrderItemId;
import com.sososhopping.entity.store.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
    boolean existsByItem(Item item);
}
