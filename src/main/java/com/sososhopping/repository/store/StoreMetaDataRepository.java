package com.sososhopping.repository.store;

import com.sososhopping.entity.owner.StoreMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreMetaDataRepository extends JpaRepository<StoreMetadata, Long> {

    boolean existsByBusinessNumber(String businessNumber);
}
