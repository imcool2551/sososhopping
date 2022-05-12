package com.sososhopping.domain.report.repository;

import com.sososhopping.entity.admin.UserReport;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {

    @EntityGraph(attributePaths = {"user", "store", "store.owner", "store.storeMetadata"})
    List<UserReport> findByHandled(boolean handled);
}
