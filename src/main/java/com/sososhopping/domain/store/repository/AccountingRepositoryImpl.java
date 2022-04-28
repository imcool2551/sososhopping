package com.sososhopping.domain.store.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sososhopping.entity.store.Accounting;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.sososhopping.entity.store.QAccounting.accounting;
import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

public class AccountingRepositoryImpl implements AccountingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public AccountingRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Accounting> findAccountingsByMonth(LocalDate localDate) {
        LocalDate start = localDate.with(firstDayOfMonth());
        LocalDate end = localDate.with(lastDayOfMonth()).plusDays(1);
        return queryFactory
                .select(accounting)
                .from(accounting)
                .where(accounting.date.between(start.atStartOfDay(), end.atStartOfDay()))
                .fetch();
    }

}
