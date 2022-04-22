package com.sososhopping.repository.store;

import com.sososhopping.entity.store.StoreMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreMetaDataRepository extends JpaRepository<StoreMetaData, Long> {

    boolean existsByBusinessNumber(String businessNumber);
}
