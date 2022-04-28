package com.sososhopping.domain.point.service;

import com.sososhopping.domain.owner.service.OwnerValidationService;
import com.sososhopping.domain.point.dto.request.UpdateSaveRateDto;
import com.sososhopping.entity.store.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class StorePointService {

    private final OwnerValidationService ownerValidationService;

    public BigDecimal findSaveRate(Long ownerId, Long storeId) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);
        return store.getSaveRate();
    }

    public void updateSaveRate(Long ownerId, Long storeId, UpdateSaveRateDto dto) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);

        boolean pointPolicyStatus = dto.getPointPolicyStatus();
        BigDecimal saveRate = pointPolicyStatus
                ? dto.getSaveRate()
                : BigDecimal.valueOf(0);
        store.updateSaveRate(pointPolicyStatus, saveRate);
    }
}
