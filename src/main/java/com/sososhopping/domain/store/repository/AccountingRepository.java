package com.sososhopping.domain.store.repository;

import com.sososhopping.entity.store.Accounting;
import com.sososhopping.entity.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountingRepository extends JpaRepository<Accounting, Long>, AccountingRepositoryCustom {

    @Query(value = "select a  from Accounting a where a.store =:store " +
            "and a.date between :start and :end order by a.date asc ")
    List<Accounting> findAccountingsByStoreAndPeriod(@Param("store") Store store
            , @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
