package com.sososhopping.server.repository.store;

import com.sososhopping.server.entity.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long>, UserStoreRepository {
}
