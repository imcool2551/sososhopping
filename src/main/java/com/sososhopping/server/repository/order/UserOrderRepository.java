package com.sososhopping.server.repository.order;

import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.orders.Order;
import com.sososhopping.server.entity.orders.OrderStatus;
import com.sososhopping.server.entity.orders.OrderType;

import java.util.List;

public interface UserOrderRepository {
    List<Order> findOrderListByUserAndOrderStatus(User user, OrderStatus ... type);
}
