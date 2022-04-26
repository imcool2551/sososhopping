package com.sososhopping.service.owner;

import com.sososhopping.common.dto.owner.request.StoreAccountingRequestDto;
import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.entity.store.Accounting;
import com.sososhopping.entity.store.Store;
import com.sososhopping.domain.store.repository.AccountingRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreAccountingService {

    private final AccountingRepository accountingRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public List<Accounting> readAccountingList(Long storeId, String yearMonth) {
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        LocalDateTime start = LocalDateTime.parse(yearMonth+"/01 00:00:00",
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

        LocalDateTime end = start.plusMonths(1).minusSeconds(1);

       return accountingRepository.findAccountingsByStoreAndPeriod(store, start, end);
    }

    @Transactional
    public void createAccounting(Long storeId, StoreAccountingRequestDto dto) {
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        Accounting accounting = Accounting.builder()
                .store(store)
                .date(LocalDateTime.parse(dto.getDate() + ":00",
                        DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                .amount(dto.getAmount())
                .description(dto.getDescription())
                .build();

        accountingRepository.save(accounting);
    }

    @Transactional
    public Accounting readAccounting(Long storeId, Long accountingId) {
        Accounting accounting = accountingRepository.findById(accountingId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 기록입니다"));

        return accounting;
    }

    @Transactional
    public void updateAccounting(Long storeId, Long accountingId
            ,StoreAccountingRequestDto dto) {
        Accounting accounting = accountingRepository.findById(accountingId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 기록입니다"));

        accounting.update(dto);
    }

    @Transactional
    public void deleteAccounting(Long storeId, Long accountingId) {
        accountingRepository.deleteById(accountingId);
    }
}
