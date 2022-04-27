package com.sososhopping.domain.store.repository;

import com.sososhopping.entity.store.Accounting;

import java.time.LocalDate;
import java.util.List;

public interface AccountingRepositoryCustom {

    List<Accounting> findAccountingsByMonth(LocalDate month);
}
