package com.sososhopping.repository.order;

import com.sososhopping.entity.member.User;
import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.orders.OrderStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface UserOrderRepository {
    List<Order> findOrderListByUserAndOrderStatus(User user, OrderStatus ... type);

    Slice<Order> findOrdersByUserAndOrderStatus(User user, Pageable pageable, OrderStatus... status);
}
