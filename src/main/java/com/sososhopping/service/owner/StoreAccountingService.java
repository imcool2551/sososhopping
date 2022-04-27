package com.sososhopping.service.owner;

import com.sososhopping.domain.store.repository.AccountingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreAccountingService {

    private final AccountingRepository accountingRepository;



    @Transactional
    public void deleteAccounting(Long storeId, Long accountingId) {
        accountingRepository.deleteById(accountingId);
    }
}
