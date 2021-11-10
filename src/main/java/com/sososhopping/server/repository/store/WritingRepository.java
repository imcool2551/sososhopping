package com.sososhopping.server.repository.store;

import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.entity.store.Writing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WritingRepository extends JpaRepository<Writing, Long> {

    List<Writing> findByStore(Store store);
}
