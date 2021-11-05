package com.sososhopping.server.repository.store;

import com.sososhopping.server.entity.store.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
