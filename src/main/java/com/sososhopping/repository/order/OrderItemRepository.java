package com.sososhopping.repository.order;

import com.sososhopping.entity.orders.OrderItem;
import com.sososhopping.entity.orders.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    boolean existsByItem(Item item);
}
