package com.sososhopping.domain.store.service;

import com.sososhopping.domain.owner.service.OwnerValidationService;
import com.sososhopping.domain.store.dto.request.CreateAccountingDto;
import com.sososhopping.domain.store.dto.response.StoreAccountingResponse;
import com.sososhopping.domain.store.repository.AccountingRepository;
import com.sososhopping.entity.store.Accounting;
import com.sososhopping.entity.store.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountingService {

    private final OwnerValidationService ownerValidationService;
    private final AccountingRepository accountingRepository;

    public Long createAccounting(Long ownerId, Long storeId, CreateAccountingDto dto) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);
        Accounting accounting = accountingRepository.save(dto.toEntity(store));
        return accounting.getId();
    }

    public List<StoreAccountingResponse> findAccountings(Long ownerId, Long storeId, LocalDate localDate) {
        ownerValidationService.validateStoreOwner(ownerId, storeId);
        return accountingRepository.findAccountingsByMonth(localDate)
                .stream()
                .map(accounting -> new StoreAccountingResponse(storeId, accounting))
                .collect(Collectors.toList());
    }
}
