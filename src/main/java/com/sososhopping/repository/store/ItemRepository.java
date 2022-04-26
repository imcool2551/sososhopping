package com.sososhopping.repository.store;

import com.sososhopping.entity.store.Item;
import com.sososhopping.entity.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByStore(Store store);

    List<Item> findByIdIn(List<Long> ids);
}
