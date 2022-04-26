package com.sososhopping.domain.store.repository;

import com.sososhopping.entity.store.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByIdIn(List<Long> ids);
}
