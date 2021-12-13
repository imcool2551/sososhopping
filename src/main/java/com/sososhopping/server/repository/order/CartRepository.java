package com.sososhopping.server.repository.order;

import com.sososhopping.server.entity.member.Cart;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.store.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long>, UserCartRepository {

    boolean existsByUserAndItem(User user, Item item);

    Optional<Cart> findByUserAndItem(User user, Item item);

    List<Cart> findByUser(User user);

    boolean existsByItem(Item item);

    void deleteByItem(Item item);
}
