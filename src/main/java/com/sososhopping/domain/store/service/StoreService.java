package com.sososhopping.domain.store.service;

import com.sososhopping.common.exception.ForbiddenException;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.common.service.S3Service;
import com.sososhopping.domain.owner.repository.OwnerRepository;
import com.sososhopping.domain.store.dto.request.CreateStoreDto;
import com.sososhopping.domain.store.dto.response.StoreResponse;
import com.sososhopping.domain.store.dto.response.StoresResponse;
import com.sososhopping.domain.store.exception.DuplicateBusinessNumberException;
import com.sososhopping.domain.store.exception.MissingFileException;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final S3Service s3Service;

    public String upload(Long ownerId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new MissingFileException("missing file");
        }

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(UnAuthorizedException::new);

        String dirPath = owner.getId().toString();
        return s3Service.upload(dirPath, file);
    }

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
        for (StoreBusinessDay storeBusinessDay : storeBusinessDays) {
            storeBusinessDayRepository.save(storeBusinessDay);
        }

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
        Store store = validateOwnership(ownerId, storeId);
        return new StoreResponse(store);
    }

    public boolean setOpen(Long ownerId, Long storeId, boolean open) {
        Store store = validateOwnership(ownerId, storeId);
        store.updateOpen(open);
        return store.isOpen();
    }

    private Store validateOwnership(Long ownerId, Long storeId) {
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
