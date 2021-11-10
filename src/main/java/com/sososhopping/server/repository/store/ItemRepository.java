package com.sososhopping.server.repository.store;

import com.sososhopping.server.entity.store.Item;
import com.sososhopping.server.entity.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByStore(Store store);
}
