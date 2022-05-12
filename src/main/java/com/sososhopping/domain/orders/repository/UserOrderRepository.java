package com.sososhopping.domain.orders.repository;

import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.orders.OrderStatus;
import com.sososhopping.entity.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface UserOrderRepository {
    Slice<Order> findOrdersByUserAndOrderStatus(User user, List<OrderStatus> statuses, Pageable pageable);
}
