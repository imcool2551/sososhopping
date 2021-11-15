package com.sososhopping.server.repository.store;

import com.sososhopping.server.entity.store.StoreLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreLogRepository extends JpaRepository<StoreLog, Long> {
}
