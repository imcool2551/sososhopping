package com.sososhopping.repository.store;

import com.sososhopping.entity.owner.Store;
import com.sososhopping.entity.owner.Writing;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WritingRepository extends JpaRepository<Writing, Long> {

    Slice<Writing> findByStoreOrderByCreatedAtDesc(Store store, Pageable pageable);
}