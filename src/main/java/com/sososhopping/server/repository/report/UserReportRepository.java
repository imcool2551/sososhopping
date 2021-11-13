package com.sososhopping.server.repository.report;

import com.sososhopping.server.entity.report.UserReport;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {

    @EntityGraph(attributePaths = {"user", "store", "store.owner", "store.storeMetaData"})
    List<UserReport> findByHandled(boolean handled);
}
