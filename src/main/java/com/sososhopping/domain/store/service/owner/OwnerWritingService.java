package com.sososhopping.domain.store.service.owner;

import com.sososhopping.domain.owner.service.OwnerValidationService;
import com.sososhopping.domain.store.dto.owner.request.CreateWritingDto;
import com.sososhopping.domain.store.repository.WritingRepository;
import com.sososhopping.entity.store.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OwnerWritingService {

    private final OwnerValidationService ownerValidationService;
    private final WritingRepository writingRepository;

    public void createWriting(Long ownerId, Long storeId, CreateWritingDto dto) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);
        writingRepository.save(dto.toEntity(store));
    }
}
