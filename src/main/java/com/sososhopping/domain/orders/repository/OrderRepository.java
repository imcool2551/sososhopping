package com.sososhopping.domain.orders.repository;

import com.sososhopping.a.OwnerOrderRepository;
import com.sososhopping.entity.orders.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, UserOrderRepository, OwnerOrderRepository {

    @EntityGraph(attributePaths = {"store.owner", "orderItems.item"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Order> findOrderDetailsById(Long orderId);

}
