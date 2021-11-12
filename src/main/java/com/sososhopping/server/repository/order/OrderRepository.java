package com.sososhopping.server.repository.order;

import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.orders.Order;
import com.sososhopping.server.entity.orders.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, UserOrderRepository {
}
