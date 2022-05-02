package com.sososhopping.domain.orders.repository;

import com.sososhopping.entity.user.Cart;
import com.sososhopping.entity.user.User;
import com.sososhopping.entity.store.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    boolean existsByUserAndItem(User user, Item item);

    Optional<Cart> findByUserAndItem(User user, Item item);

    List<Cart> findByUser(User user);

}
