package com.sososhopping.domain.store.service;

import com.sososhopping.common.exception.ForbiddenException;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.owner.repository.OwnerRepository;
import com.sososhopping.domain.store.dto.request.CreateStoreDto;
import com.sososhopping.domain.store.dto.response.StoreResponse;
import com.sososhopping.domain.store.dto.response.StoresResponse;
import com.sososhopping.domain.store.exception.DuplicateBusinessNumberException;
import com.sososhopping.domain.store.repository.StoreBusinessDayRepository;
import com.sososhopping.domain.store.repository.StoreMetaDataRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.entity.owner.Owner;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.store.StoreBusinessDay;
import com.sososhopping.entity.store.StoreMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {

    private final OwnerRepository ownerRepository;
    private final StoreRepository storeRepository;
    private final StoreMetaDataRepository storeMetaDataRepository;
    private final StoreBusinessDayRepository storeBusinessDayRepository;

    public Long createStore(Long ownerId, CreateStoreDto dto) {
        String businessNumber = dto.getBusinessNumber();
        if (storeMetaDataRepository.existsByBusinessNumber(businessNumber)) {
            throw new DuplicateBusinessNumberException("businessNumber already in use: " + businessNumber);
        }

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(UnAuthorizedException::new);
        StoreMetadata storeMetadata = dto.toStoreMetadata();
        Store store = dto.toStore(owner, storeMetadata);
        List<StoreBusinessDay> storeBusinessDays = dto.toStoreBusinessDays(store);

        storeMetaDataRepository.save(storeMetadata);
        storeRepository.save(store);
        storeBusinessDays.forEach(storeBusinessDayRepository::save);

        return store.getId();
    }

    public List<StoresResponse> findStores(Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(UnAuthorizedException::new);
        List<Store> stores = storeRepository.findByOwner(owner);

        return stores.stream()
                .map(StoresResponse::new)
                .collect(Collectors.toList());
    }

    public StoreResponse findStore(Long ownerId, Long storeId) {
        Store store = validateStoreOwner(ownerId, storeId);
        return new StoreResponse(store);
    }

    public boolean setOpen(Long ownerId, Long storeId, boolean open) {
        Store store = validateStoreOwner(ownerId, storeId);
        store.updateOpen(open);
        return store.isOpen();
    }

    public Store validateStoreOwner(Long ownerId, Long storeId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(UnAuthorizedException::new);

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("Store not found with id " + storeId));

        if (!store.belongsTo(owner)) {
            throw new ForbiddenException("Store does not belong to owner");
        }

        return store;
    }
}
