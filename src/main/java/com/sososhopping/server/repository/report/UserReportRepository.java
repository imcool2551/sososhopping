package com.sososhopping.server.repository.report;

import com.sososhopping.server.entity.report.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {
}
