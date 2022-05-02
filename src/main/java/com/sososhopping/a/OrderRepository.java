package com.sososhopping.a;

import com.sososhopping.entity.orders.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, UserOrderRepository, OwnerOrderRepository {

    @Override
    @EntityGraph(attributePaths = {"store"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Order> findById(Long orderId);
}
