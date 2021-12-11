package com.sososhopping.server.repository.store;

import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.entity.store.Writing;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WritingRepository extends JpaRepository<Writing, Long> {

    Slice<Writing> findByStoreOrderByCreatedAtDesc(Store store, Pageable pageable);
}