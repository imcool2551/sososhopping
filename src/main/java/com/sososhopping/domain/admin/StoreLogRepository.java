package com.sososhopping.domain.admin;

import com.sososhopping.entity.store.StoreLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreLogRepository extends JpaRepository<StoreLog, Long> {
}
