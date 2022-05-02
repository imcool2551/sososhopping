package com.sososhopping.a;

import com.sososhopping.entity.orders.OrderItem;
import com.sososhopping.entity.store.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    boolean existsByItem(Item item);
}
