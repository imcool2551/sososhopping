package com.sososhopping.repository.store;

import com.sososhopping.entity.report.StoreReport;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreReportRepository extends JpaRepository<StoreReport, Long> {

    @EntityGraph(attributePaths = {"user", "store", "store.owner", "store.storeMetaData"})
    List<StoreReport> findByHandled(boolean handled);
}
