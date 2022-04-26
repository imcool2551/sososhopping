package com.sososhopping.domain.store.service;

import com.sososhopping.domain.store.dto.request.CreateAccountingDto;
import com.sososhopping.domain.store.repository.AccountingRepository;
import com.sososhopping.entity.store.Accounting;
import com.sososhopping.entity.store.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountingService {

    private final StoreService storeService;
    private final AccountingRepository accountingRepository;

    public Long createAccounting(Long ownerId, Long storeId, CreateAccountingDto dto) {
        Store store = storeService.validateStoreOwner(ownerId, storeId);
        Accounting accounting = accountingRepository.save(dto.toEntity(store));
        return accounting.getId();
    }
}
