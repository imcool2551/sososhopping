package com.sososhopping.domain.store.repository;

import com.sososhopping.entity.store.Accounting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountingRepository extends JpaRepository<Accounting, Long>, AccountingRepositoryCustom {
}
