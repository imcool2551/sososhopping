package com.sososhopping.service.owner;

import com.sososhopping.common.dto.owner.request.StoreAccountingRequestDto;
import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.domain.store.repository.AccountingRepository;
import com.sososhopping.entity.store.Accounting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreAccountingService {

    private final AccountingRepository accountingRepository;

    @Transactional
    public Accounting readAccounting(Long storeId, Long accountingId) {
        Accounting accounting = accountingRepository.findById(accountingId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 기록입니다"));

        return accounting;
    }


    @Transactional
    public void deleteAccounting(Long storeId, Long accountingId) {
        accountingRepository.deleteById(accountingId);
    }
}
