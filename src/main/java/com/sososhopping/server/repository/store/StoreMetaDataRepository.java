package com.sososhopping.server.repository.store;

import com.sososhopping.server.entity.store.StoreMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreMetaDataRepository extends JpaRepository<StoreMetaData, Long> {

    boolean existsByBusinessNumber(String businessNumber);
}
