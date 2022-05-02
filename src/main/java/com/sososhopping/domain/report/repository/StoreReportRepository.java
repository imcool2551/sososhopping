package com.sososhopping.domain.report.repository;

import com.sososhopping.entity.admin.StoreReport;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreReportRepository extends JpaRepository<StoreReport, Long> {

    @EntityGraph(attributePaths = {"user", "store", "store.owner", "store.storeMetaData"})
    List<StoreReport> findByHandled(boolean handled);
}
