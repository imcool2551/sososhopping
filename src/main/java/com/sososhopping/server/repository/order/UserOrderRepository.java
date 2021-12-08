package com.sososhopping.server.repository.order;

import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.orders.Order;
import com.sososhopping.server.entity.orders.OrderStatus;
import com.sososhopping.server.entity.orders.OrderType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface UserOrderRepository {
    List<Order> findOrderListByUserAndOrderStatus(User user, OrderStatus ... type);

    Slice<Order> findOrdersByUserAndOrderStatus(User user, Pageable pageable, OrderStatus... status);
}
